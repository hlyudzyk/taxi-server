package com.taxiapp.main.services.user;

import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.net.responses.UserDataResponse;
import com.taxiapp.main.security.user.ChangePasswordRequest;
import com.taxiapp.main.security.user.Role;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.exceptions.NoContentException;
import com.taxiapp.main.services.filter.user.UsersFilterChain;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }


    public List<UserDataResponse> getAllUsers(FilterUsersRequest filterUsersRequest){
        List<User> users = (new UsersFilterChain()).filterUsers(userRepository.findAll(),filterUsersRequest);
        List<UserDataResponse> userDataResponses = new ArrayList<>();

        for (User user : users) {
            userDataResponses.add(new UserDataResponse(user));
        }
        return userDataResponses;
    }

    public List<UserDataResponse> getAllUsersByRole(Role role){
        List<User> users = userRepository.findAllByRole(role);
        List<UserDataResponse> userDataResponse = new ArrayList<>();

        for(User u:users){
            userDataResponse.add(new UserDataResponse(u));
        }

        return userDataResponse;
    }

    public UserDataResponse getUserById(UUID id){
        User user = userRepository.findById(id).orElseThrow(()->
            new NoContentException("No user matching id " + id + " has been found"));

        return new UserDataResponse(user);
    }
}
