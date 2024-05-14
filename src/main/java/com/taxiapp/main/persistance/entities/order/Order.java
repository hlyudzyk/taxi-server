package com.taxiapp.main.persistance.entities.order;

import com.taxiapp.main.persistance.entities.car.Car;
import com.taxiapp.main.security.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private String pickupLocation;
    private String deliveryLocation;

    private LocalDateTime orderedAt;
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    private TaxiType taxiType;

    private double distance;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
