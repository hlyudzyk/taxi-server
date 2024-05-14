package com.taxiapp.main.services.order;


import static org.springframework.data.domain.PageRequest.of;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxiapp.main.net.requests.order.CancelOrderRequest;
import com.taxiapp.main.net.requests.order.ConfirmOrderCompletionRequest;
import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.net.requests.order.NewOrderRequest;
import com.taxiapp.main.net.responses.OrderDataResponse;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import com.taxiapp.main.persistance.repositories.OrdersRepository;
import com.taxiapp.main.processing.OrdersProcessingService;
import com.taxiapp.main.processing.onlinedriver.Driver;
import com.taxiapp.main.processing.onlinedriver.DriverStatus;
import com.taxiapp.main.processing.onlinedriver.OnlineDriversRegistry;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.exceptions.NoContentException;
import com.taxiapp.main.services.filter.order.OrdersFilterChain;
import com.taxiapp.main.services.util.PriceCalculator;
import com.taxiapp.main.services.util.positioning.Coordinates;
import com.taxiapp.main.services.util.positioning.LocationService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Validated
public class OrderService {
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersProcessingService ordersProcessingService;
    private final LocationService locationService;
    private final OrdersFilterChain ordersFilter;
    private final OnlineDriversRegistry onlineDriversRegistry;
    private final AuditorAware<UUID> auditorAware;
    private final PriceCalculator priceCalculator;


    public OrderDataResponse createOrder(NewOrderRequest newOrderRequest) {

        User user = getUserById(newOrderRequest.getUserId());

        ObjectMapper objectMapper = new ObjectMapper();
        Coordinates pickupLocationCoordinates;
        Coordinates deliveryLocationCoordinates;

        try {
            pickupLocationCoordinates = objectMapper.readValue(newOrderRequest.getPickupLocation(),Coordinates.class);
            deliveryLocationCoordinates = objectMapper.readValue(newOrderRequest.getDeliveryLocation(),Coordinates.class);
        } catch (IOException e) {throw new ConstraintViolationException("Invalid coordinates input",null);}


        Order order = Order.builder()
            .user(user)
            .taxiType(newOrderRequest.getTaxiType())
            .orderedAt(LocalDateTime.now())
            .pickupLocation("Street 12"
//                locationService.getGeocodedLocation(pickupLocationCoordinates.latitude(),
//                pickupLocationCoordinates.longitude()).orElseThrow()
            )
            .deliveryLocation(
                "Street 14"
//                locationService.getGeocodedLocation(deliveryLocationCoordinates.latitude(),
//                deliveryLocationCoordinates.longitude()).orElseThrow()
            )
            .orderStatus(OrderStatus.PENDING)
            .build();

        order.setDistance(
            locationService.calculateDistance(
                pickupLocationCoordinates.latitude(),
                pickupLocationCoordinates.longitude(),
                deliveryLocationCoordinates.latitude(),
                deliveryLocationCoordinates.longitude()
        ));

        order.setTotalPrice(priceCalculator.calculatePrice(order));

        Order savedOrder = ordersRepository.save(order);
        ordersProcessingService.offerOrderToQueue(savedOrder);

        //TODO Replace with logger or remove
        System.out.println("New order has been put to queue " + savedOrder.getId());
        return new OrderDataResponse(savedOrder);
    }


    public boolean cancelOrder(CancelOrderRequest cancelOrderRequest, Principal connectedUser){
         var currentUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

         UUID orderIdToCancel = UUID.fromString(cancelOrderRequest.getOrderId());
         Order orderToCancel = getOrderById(orderIdToCancel);


         if (orderToCancel != null && orderToCancel.getUser().getId().equals(currentUser.getId())) {
            orderToCancel.setTotalPrice(0);
            orderToCancel.setFinishedAt(LocalDateTime.now());
            orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
            ordersRepository.save(orderToCancel);
            ordersProcessingService.removeOrderFromOnlineRegistry(orderToCancel);
        }

        return true;
    }


    public void confirmOrderCompletion(ConfirmOrderCompletionRequest request){
        Order order = ordersRepository.findById(UUID.fromString(request.getOrderId())).orElseThrow();
        Driver driver = onlineDriversRegistry.getDriver(order.getDriver().getId());

        driver.setDriverStatus(DriverStatus.AVAILABLE);
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setTotalPrice(priceCalculator.calculatePrice(order));
        order.setFinishedAt(LocalDateTime.now());
        System.out.println("Order " + order.getId() +" has been completed!");
        ordersRepository.save(order);
        ordersProcessingService.removeOrderFromOnlineRegistry(order);
    }


    public List<OrderDataResponse> getOrdersWithFilter(@Valid FilterOrdersRequest filterOrdersRequest,int page,int limit)
    {
        if(page==0&&limit==0) limit = getTotalOrders();

        List<Order> orders =  ordersFilter.filterOrders(
            ordersRepository.findAll(of(page, limit)).getContent(),filterOrdersRequest);

        return orders.stream().map(OrderDataResponse::new).toList();
    }

    public int getTotalOrders(){
        return ordersRepository.findAll().size();
    }

    private User getUserById(String id) {
        return userRepository.findById(UUID.fromString(id))
            .orElseThrow(()->new NoContentException("No user found by given id"));
    }


    public Order getOrderById(UUID orderId) {
        return ordersRepository.findById(orderId).orElseThrow(()->new NoContentException("Cannot find order with id " + orderId));
    }
}
