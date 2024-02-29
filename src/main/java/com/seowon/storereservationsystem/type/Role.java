package com.seowon.storereservationsystem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER("USER"),
    OWNER("OWNER");

    private final String role;
}
