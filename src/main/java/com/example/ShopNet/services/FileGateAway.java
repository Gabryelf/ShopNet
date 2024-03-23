package com.example.ShopNet.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mcInput")
public interface FileGateAway {
    @Gateway
    void writeToFife(@Header(FileHeaders.FILENAME) String filename, String data);
}

