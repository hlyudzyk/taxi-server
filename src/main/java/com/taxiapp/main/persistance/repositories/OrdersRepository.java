package com.taxiapp.main.persistance.repositories;

import com.taxiapp.main.persistance.entities.order.Order;
import com.taxiapp.main.persistance.entities.order.OrderStatus;
import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByDriverId(UUID id);
    List<Order> findAllByUserId(UUID id);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByOrderedAtAfter(LocalDateTime dateTime);
    List<Order> findAllByOrderedAtBefore(LocalDateTime dateTime);
    List<Order> findAllByOrderedAtBeforeAndOrderedAtAfter(LocalDateTime dateBefore,LocalDateTime dateAfter);
}
