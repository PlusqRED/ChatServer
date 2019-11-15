package com.grape.model;

import lombok.Getter;

@Getter
public enum UserServerConvention {
    DISCONNECTED("/disconnected");

    private final String command;

    UserServerConvention(String command) {
        this.command = command;
    }
}
