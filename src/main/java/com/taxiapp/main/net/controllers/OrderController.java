package com.taxiapp.main.net.controllers;


import com.taxiapp.main.net.requests.order.CancelOrderRequest;
import com.taxiapp.main.net.requests.order.ConfirmOrderCompletionRequest;
import com.taxiapp.main.net.requests.order.FilterOrdersRequest;
import com.taxiapp.main.net.requests.order.NewOrderRequest;
import com.taxiapp.main.net.responses.OrderDataResponse;
import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import com.taxiapp.main.persistance.entities.order.TaxiType;
import com.taxiapp.main.services.order.OrderService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<OrderDataResponse> getOrder(@RequestParam String id) {
        Order order = orderService.getOrderById(UUID.fromString(id));
        return ResponseEntity.ok(new OrderDataResponse(order));
    }

    @PostMapping("/new")
    public ResponseEntity<OrderDataResponse> createOrder(
        @Valid @RequestBody NewOrderRequest newOrderRequest) {
        return ResponseEntity.ok(
            orderService.createOrder(newOrderRequest)
        );
    }

    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancelOrder(
        @RequestBody CancelOrderRequest cancelOrderRequest,
        Principal connectedUser) {

        return ResponseEntity.ok(orderService.cancelOrder(cancelOrderRequest, connectedUser));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDataResponse>> getAllOrders(
        @RequestParam(required = false) String userId,
        @RequestParam(required = false) String driverId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeAfter,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timeBefore,
        @RequestParam(required = false) String pickupLocation,
        @RequestParam(required = false) String deliveryLocation,
        @RequestParam(required = false) TaxiType taxiType,
        @RequestParam(required = false) OrderStatus orderStatus,
        @RequestParam(required = false) Optional<Integer> page,
        @RequestParam(required = false) Optional<Integer> limit) {

        FilterOrdersRequest filterOrdersRequest = FilterOrdersRequest.builder()
            .userId(userId)
            .driverId(driverId)
            .timeAfter(timeAfter)
            .timeBefore(timeBefore)
            .pickupLocation(pickupLocation)
            .deliveryLocation(deliveryLocation)
            .taxiType(taxiType)
            .orderStatus(orderStatus)
            .build();

        List<OrderDataResponse> orders = orderService.getOrdersWithFilter(filterOrdersRequest,
            page.orElse(0),limit.orElse(10));

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }


    @PostMapping("/confirm")
    public ResponseEntity<Boolean> confirmOrderCompletion(
        @Valid @RequestBody ConfirmOrderCompletionRequest request) {
        orderService.confirmOrderCompletion(request);
        return ResponseEntity.ok(true);

    }



}
