package com.taxiapp.main;

import static com.taxiapp.main.security.user.Role.DRIVER;
import static com.taxiapp.main.security.user.Role.USER;

import com.github.javafaker.Faker;
import com.taxiapp.main.net.requests.order.NewOrderRequest;
import com.taxiapp.main.net.responses.OrderDataResponse;
import com.taxiapp.main.security.auth.AuthenticationService;
import com.taxiapp.main.security.auth.RegisterRequest;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.order.OrderService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

}
