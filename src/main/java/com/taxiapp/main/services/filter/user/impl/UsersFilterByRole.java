package com.taxiapp.main.services.filter.user.impl;

import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.security.user.Role;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.services.filter.user.UserFilter;
import java.util.List;

public class UsersFilterByRole extends UserFilter {

    public UsersFilterByRole(UserFilter next) {
        super(next);
    }

    @Override
    public List<User> filter(List<User> users, FilterUsersRequest filter) {
        Role role = filter.getRole();
        if(!isFilterStepRequested(role)) return users;
        users = users.stream().filter(u->u.getRole().equals(role)).toList();
        return users;
    }
}
