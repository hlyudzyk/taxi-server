package com.taxiapp.main.services.filter.user.impl;

import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.services.filter.user.UserFilter;
import java.util.List;

public class UsersFilterByEmail extends UserFilter {

    public UsersFilterByEmail(UserFilter next) {
        super(next);
    }

    @Override
    public List<User> filter(List<User> users, FilterUsersRequest filter) {
        String userEmail = filter.getUserEmail();
        if(!isFilterStepRequested(userEmail)) return users;
        users = users.stream().filter(u->u.getEmail().contains(userEmail)).toList();
        return users;
    }
}
