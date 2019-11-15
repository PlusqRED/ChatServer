package com.grape;

import com.grape.config.GlobalConfig;
import com.grape.config.SpringConfiguration;
import com.grape.server.Connection;
import com.grape.service.SocketManager;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component
@Log
public class Launcher {

    private final Connection connection;
    private final SocketManager socketManager;
    private final GlobalConfig globalConfig;

    public Launcher(Connection connection, SocketManager socketManager, GlobalConfig globalConfig) {
        this.connection = connection;
        this.socketManager = socketManager;
        this.globalConfig = globalConfig;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        context.getBean(Launcher.class).run();
    }

    @SneakyThrows
    private void run() {
        log.info("Server is started");
        while (globalConfig.getServerOnline().get()) {
            Socket userSocket = connection.getSSocket().accept();
            log.info("New user accepter: " + userSocket.getInetAddress());
            globalConfig.getForkJoinPool()
                    .submit(() -> socketManager.apply(userSocket));
        }
        log.info("Server stopped!");
    }
}
