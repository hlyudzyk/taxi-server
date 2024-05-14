package com.taxiapp.main.services.filter.user.impl;

import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.services.filter.user.UserFilter;
import java.util.List;

public class UsersFilterById extends UserFilter {

    public UsersFilterById(UserFilter next) {
        super(next);
    }

    @Override
    public List<User> filter(List<User> users, FilterUsersRequest filter) {
        String userId = filter.getUserId();
        if(!isFilterStepRequested(userId)) return users;
        users = users.stream().filter(u->u.getId().toString().contains(userId)).toList();
        return users;
    }
}
