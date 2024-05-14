package com.taxiapp.main;

import static com.taxiapp.main.security.user.Role.ADMIN;
import static com.taxiapp.main.security.user.Role.DRIVER;
import static com.taxiapp.main.security.user.Role.USER;

import com.github.javafaker.Faker;
import com.taxiapp.main.net.requests.ChangeDriverStatusRequest;
import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.net.requests.order.NewOrderRequest;
import com.taxiapp.main.persistance.entities.order.TaxiType;
import com.taxiapp.main.processing.onlinedriver.DriverStatus;
import com.taxiapp.main.security.auth.AuthenticationService;
import com.taxiapp.main.security.auth.RegisterRequest;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.driver.DriverService;
import com.taxiapp.main.services.order.OrderService;
import com.taxiapp.main.services.user.UserService;
import com.taxiapp.main.services.util.positioning.Coordinates;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
public class MainApplication {

	private final UserService userService;

	public MainApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
		AuthenticationService service) {

			return args -> {
				try {
					var admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("admin@mail.com")
						.password("password")
						.role(ADMIN)
						.birthday(LocalDate.of(2000, 5, 21))
						.build();
					System.out.println("Admin token: " + service.register(admin).getAccessToken());

					var user = RegisterRequest.builder()
						.firstname("user")
						.lastname("user")
						.email("user@mail.com")
						.password("password")
						.role(USER)
						.birthday(LocalDate.of(2002, 1, 3))
						.build();
					System.out.println("User token: " + service.register(user).getAccessToken());
				} catch (ConstraintViolationException ex) {
					System.out.println("Admin already registered!");
				}
			};

	}
}
