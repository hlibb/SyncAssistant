package com.example.sync.demo.client;

import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    @SneakyThrows
    public static void main(String[] args) {
        //System.out.println(InetAddress.getLocalHost().getHostAddress()); ws://127.0.1.1:8080/websocket
        try (var socket = new Socket("95.223.45.239", 8899)){
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            var message = "Hello \n";
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
            System.out.println("Client error");
        }
    }
}
