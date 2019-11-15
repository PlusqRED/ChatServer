package com.grape.service;

import com.grape.model.User;

public interface UserService {
    void service(User user);

    String findOutLogin(User user);
}
