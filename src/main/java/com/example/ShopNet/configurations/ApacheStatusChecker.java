package com.example.ShopNet.configurations;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.Socket;

@Component
public class ApacheStatusChecker {

    public boolean isApacheRunning(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            // Если сокет успешно открыт, сервис работает
            return true;
        } catch (IOException e) {
            // Если возникла ошибка, сервис не работает
            return false;
        }
    }
}



