package com.grape.service.impl;

import com.grape.config.GlobalConfig;
import com.grape.model.User;
import com.grape.service.SocketManager;
import com.grape.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
@Log
public class DefaultSocketManager implements SocketManager {

    private final GlobalConfig globalConfig;
    private final UserService userService;

    public DefaultSocketManager(GlobalConfig globalConfig, UserService userService) {
        this.globalConfig = globalConfig;
        this.userService = userService;
    }

    public void apply(Socket userSocket) {
        User user = createUser(userSocket);
        globalConfig.getConnectedUsers().add(user);
        user.setLogin(userService.findOutLogin(user));

        userService.service(user);

        globalConfig.getConnectedUsers().remove(user);
        destroyUser(user, userSocket);
        log.info("User disconnected: " + user.getLogin());
    }

    @SneakyThrows
    private void destroyUser(User user, Socket userSocket) {
        user.getReader().close();
        user.getWriter().close();
        userSocket.close();
    }

    @SneakyThrows
    private User createUser(Socket userSocket) {
        return User.builder()
                .reader(new BufferedReader(new InputStreamReader(userSocket.getInputStream())))
                .writer(new PrintWriter(userSocket.getOutputStream(), true))
                .connected(true)
                .build();
    }
}
