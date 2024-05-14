package com.taxiapp.main.services.filter.user;

import com.taxiapp.main.net.requests.FilterUsersRequest;
import com.taxiapp.main.security.user.User;
import java.util.List;

public abstract class UserFilter {
    protected UserFilter next;

    protected UserFilter(UserFilter next) {
        this.next = next;
    }

    protected abstract List<User> filter(List<User> orders, FilterUsersRequest filter);
    protected boolean isFilterStepRequested(Object o){
        return o!=null;
    }
    public List<User> doFilter(List<User> users, FilterUsersRequest filter){

        users = filter(users,filter);

        if (next != null) {
            return next.doFilter(users, filter);
        } else {
            return users;
        }
    }
}