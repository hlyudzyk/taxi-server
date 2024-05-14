package com.taxiapp.main.net.requests;

import com.taxiapp.main.security.user.Role;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewMessageRequest {
    @NotBlank(message = "Subject must be not empty")
    private String subject;
    @NotBlank(message = "Content must be not empty")
    private String content;
    private List<String> recipientsIds;
    private List<Role> targetRoles;
}
