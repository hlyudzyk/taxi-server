package com.taxiapp.main.net.controllers;

import com.taxiapp.main.net.requests.ChangeDriverStatusRequest;
import com.taxiapp.main.net.responses.DriverDataResponse;
import com.taxiapp.main.net.responses.DriverFinancialStatementResponse;
import com.taxiapp.main.processing.onlinedriver.Driver;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.driver.DriverService;
import com.taxiapp.main.services.order.OrderService;
import com.taxiapp.main.services.user.UserService;
import com.taxiapp.main.services.validation.ValidUUID;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
public class DriverController {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final DriverService driverService;
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<DriverDataResponse> getDriver(@ValidUUID @RequestParam String id) {
        Driver driver = driverService.getOnlineDriverById(UUID.fromString(id));
        return ResponseEntity.ok(new DriverDataResponse(driver));
    }

    @GetMapping ("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DriverDataResponse>> getAllDrivers(){
        List<DriverDataResponse> driverDataResponse = driverService.getAllDrivers();
        if(driverDataResponse.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(driverDataResponse);
    }

    @PostMapping("/status")
    public ResponseEntity<Boolean> changeDriverStatus(
        @Valid @RequestBody ChangeDriverStatusRequest request) {
        return ResponseEntity.ok(driverService.changeDriverStatus(request));
    }

    @GetMapping("/financial-statement")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DriverFinancialStatementResponse> getFinancialStatementForDriver(
        @ValidUUID @RequestParam String id){
        return ResponseEntity.ok(driverService.generateFinancialStatementForDriver(id));
    }


}