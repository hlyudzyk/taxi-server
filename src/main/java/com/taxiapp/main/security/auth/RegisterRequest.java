package com.taxiapp.main.security.auth;

import com.taxiapp.main.security.user.Role;
import com.taxiapp.main.services.validation.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "First name is required")
  @Size(min = 1, max = 50, message = "First name length must be between 1 and 50 characters")
  private String firstname;

  @NotBlank(message = "Last name is required")
  @Size(min = 1, max = 50, message = "Last name length must be between 1 and 50 characters")
  private String lastname;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 6, max = 100, message = "Password length must be between 6 and 100 characters")
  private String password;

  @NotNull(message = "Birthday is required")
  @Past(message = "Birthday must be in the past")
  private LocalDate birthday;

  @NotNull(message = "Role is required")
  @ValidEnum(enumClass = Role.class,message = "Invalid role given")
  private Role role;

}