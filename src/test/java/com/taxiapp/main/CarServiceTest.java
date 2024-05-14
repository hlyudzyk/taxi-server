package com.taxiapp.main;

import static com.taxiapp.main.security.user.Role.ADMIN;
import static com.taxiapp.main.security.user.Role.DRIVER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;


import com.taxiapp.main.net.requests.AssignCarRequest;
import com.taxiapp.main.net.requests.NewCarRequest;
import com.taxiapp.main.net.responses.CarDataResponse;
import com.taxiapp.main.security.auth.AuthenticationService;
import com.taxiapp.main.security.auth.RegisterRequest;
import com.taxiapp.main.services.driver.CarService;
import com.taxiapp.main.services.exceptions.NonUniqueDriverAssigningException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = MainApplication.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class CarServiceTest {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CarService carService;
    private String driverId;

    @BeforeAll
    void setDriverId(){
        var driverRegisterRequest = RegisterRequest.builder()
            .firstname("Driver")
            .lastname("Driver")
            .email("driver@mail.com")
            .password("password")
            .role(DRIVER)
            .birthday(LocalDate.of(2000,5,21))
            .build();

        driverId = authenticationService.register(driverRegisterRequest).getId();

    }

    @Test
    @Order(1)
    void testRegisterCar_Success() {
        NewCarRequest newCarRequest = NewCarRequest.builder()
            .driverId(driverId)
            .manufacturer("Skoda")
            .model("Rapid")
            .carPlate("QE1234BA")
            .productionYear(1900)
            .build();

        CarDataResponse registeredCarDataResponse = carService.registerCar(newCarRequest);

        assertNotNull(registeredCarDataResponse);
        assertEquals(newCarRequest.getCarPlate(), registeredCarDataResponse.getCarPlate());
        assertEquals(newCarRequest.getDriverId(), registeredCarDataResponse.getDriverId());
        assertEquals(newCarRequest.getModel(),registeredCarDataResponse.getCarModel());
        assertEquals(newCarRequest.getManufacturer(), registeredCarDataResponse.getCarManufacturer());

    }

    @Test
    @Order(2)
    void testRegisterCar_ThrowNonUniqueDriverException() {
        NewCarRequest secondCarRequest = NewCarRequest.builder()
            .driverId(driverId)
            .manufacturer("Toyota")
            .model("Camry")
            .carPlate("WE1232QW")
            .productionYear(1998)
            .build();

        assertThrows(NonUniqueDriverAssigningException.class, () -> carService.registerCar(secondCarRequest));

    }

    @Test
    @Order(3)
    void testAssignCar(){
        var otherDriverRegisterRequest = RegisterRequest.builder()
            .firstname("Driver")
            .lastname("Driver")
            .email("driver@mail.com")
            .password("password")
            .role(DRIVER)
            .birthday(LocalDate.of(2000,5,21))
            .build();

        String otherDriverId = authenticationService.register(otherDriverRegisterRequest).getId();

        AssignCarRequest assignCarRequest = AssignCarRequest.builder()
                .carId(carService.findCarByDriverId(UUID.fromString(driverId)).getId().toString())
                    .driverId(otherDriverId)
                        .build();

        CarDataResponse assignedCarDataResponse = carService.assignCarToDriver(assignCarRequest);

        assertEquals(otherDriverId, assignedCarDataResponse.getDriverId());
        assertEquals(assignCarRequest.getCarId(), assignedCarDataResponse.getCarId());
    }
}