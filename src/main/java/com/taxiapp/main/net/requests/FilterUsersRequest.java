package com.taxiapp.main.net.requests;

import com.taxiapp.main.security.user.Role;
import com.taxiapp.main.services.validation.ValidEnum;
import com.taxiapp.main.services.validation.ValidUUID;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterUsersRequest {
    @ValidEnum(enumClass = Role.class, message = "Invalid role")
    private Role role;
    @ValidUUID
    private String userId;
    @Email(message = "Email format required")
    private String userEmail;
}
