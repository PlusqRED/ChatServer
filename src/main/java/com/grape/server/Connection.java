package com.grape.server;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;

@Component
@Getter
@PropertySource("classpath:properties/app.properties")
public class Connection {
    private ServerSocket sSocket;

    @Value("${server.port}")
    private int port;

    @SneakyThrows
    public Connection() {
        sSocket = new ServerSocket(port);
    }
}
