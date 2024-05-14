package com.taxiapp.main.net.responses;

import com.taxiapp.main.security.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataResponse {

    private String userId;
    private String firstname;
    private String lastname;
    private String email;

    public UserDataResponse(User user){
        this.userId = user.getId().toString();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();

    }

}
