package com.convertino.hire.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    GUEST("GUEST");

    private final String role;
}