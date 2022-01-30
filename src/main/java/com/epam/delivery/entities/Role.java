package com.epam.delivery.entities;

import java.io.Serializable;

public enum Role implements Serializable {
    ADMIN, CLIENT;

    public static Role getRole(User user) {
        int roleId = user.getRoleID();
        return Role.values()[roleId];

    }

    public String getName() {
        return name().toLowerCase();
    }
}
