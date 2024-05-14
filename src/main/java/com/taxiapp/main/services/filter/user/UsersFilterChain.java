package com.taxiapp.main.services.filter.user;

import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.services.filter.user.impl.UsersFilterByEmail;
import com.taxiapp.main.services.filter.user.impl.UsersFilterById;
import com.taxiapp.main.services.filter.user.impl.UsersFilterByRole;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UsersFilterChain {
    private UserFilter chain;

    public UsersFilterChain() {
        buildChain();
    }

    private void buildChain() {
       chain = new UsersFilterByRole(new UsersFilterByEmail(new UsersFilterById(null )));
    }

    public List<User> filterUsers(List<User> users, FilterUsersRequest filter) {
        return chain.doFilter(users, filter);
    }

}