package com.grape.config;

import com.google.gson.Gson;
import com.grape.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

@Repository
@Getter
@Setter
public class GlobalConfig {
    private AtomicBoolean serverOnline = new AtomicBoolean(true);
    private List<User> connectedUsers = Collections.synchronizedList(new ArrayList<User>());
    private Gson gson = new Gson();
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
}

