package com.grape.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.PrintWriter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private boolean connected;
    private String login;
    private BufferedReader reader;
    private PrintWriter writer;
}
