package com.grape.service.impl;

import com.grape.config.GlobalConfig;
import com.grape.model.Message;
import com.grape.model.User;
import com.grape.model.UserServerConvention;
import com.grape.service.UserService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    private final GlobalConfig globalConfig;

    public DefaultUserService(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    @SneakyThrows
    public void service(User user) {
        while (user.isConnected()) {
            Message message = globalConfig.getGson().fromJson(user.getReader().readLine(), Message.class);
            if (readyToDisconnect(message)) {
                user.setConnected(false);
            } else {
                distributeMessage(message, user);
            }
        }
    }

    private boolean readyToDisconnect(Message message) {
        return message.getContent()
                .equals(UserServerConvention.DISCONNECTED.getCommand());
    }

    private void distributeMessage(Message message, User fromUser) {
        message.setContent(fromUser.getLogin() + ": " + message.getContent());
        globalConfig.getConnectedUsers().forEach(user -> send(user, message));
    }

    private void send(User target, Message message) {
        target.getWriter().println(globalConfig.getGson().toJson(message));
    }

    @SneakyThrows
    public String findOutLogin(User user) {
        return globalConfig.getGson()
                .fromJson(user.getReader().readLine(), Message.class)
                .getContent();
    }
}
