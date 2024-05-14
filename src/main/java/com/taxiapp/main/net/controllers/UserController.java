package com.taxiapp.main.net.controllers;


import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.net.responses.UserDataResponse;
import com.taxiapp.main.security.user.ChangePasswordRequest;
import com.taxiapp.main.security.user.Role;
import com.taxiapp.main.services.user.UserService;
import com.taxiapp.main.services.validation.ValidUUID;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<UserDataResponse> getUser(@ValidUUID @RequestParam String id){
        UserDataResponse userDataResponse = userService.getUserById(UUID.fromString(id));
        return ResponseEntity.ok(userDataResponse);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDataResponse>> getAllUsers(@RequestParam(required = false) String userId,
                                                              @RequestParam(required = false) Role role,
                                                              @RequestParam(required = false) String userEmail) {

        FilterUsersRequest filterUsersRequest = FilterUsersRequest.builder()
            .userId(userId)
            .userEmail(userEmail)
            .role(role)
            .build();

        List<UserDataResponse> userDataResponse = userService.getAllUsers(filterUsersRequest);
        if(userDataResponse.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userDataResponse);
    }


    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(
        @Valid @RequestBody ChangePasswordRequest request,
        Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

}
