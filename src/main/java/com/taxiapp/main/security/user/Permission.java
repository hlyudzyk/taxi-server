package com.taxiapp.main.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_PERMISSION("Admin permission");

    private final String permission;
}
