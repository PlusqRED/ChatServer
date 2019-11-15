package com.grape.server;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.ServerSocket;

@Component
@Getter
@PropertySource("classpath:properties/app.properties")
@Log
public class Connection {
    private ServerSocket sSocket;

    @Value("${server.port}")
    private int port;

    @SneakyThrows
    @PostConstruct
    private void initialize() {
        log.info("Listening port: " + port);
        sSocket = new ServerSocket(port);
    }
}
