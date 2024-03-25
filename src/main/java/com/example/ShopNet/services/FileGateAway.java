package com.example.ShopNet.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;
/**
 * Интерфейс шлюза для записи данных в файл.
 * Он использует аннотацию MessagingGateway для определения шлюза сообщений с каналом по умолчанию.
 */

@MessagingGateway(defaultRequestChannel = "mcInput")
public interface FileGateAway {
    /**
     * Шлюзный метод для записи данных в файл.
     *
     * @param filename Имя файла, в который будут записаны данные.
     * @param data     Данные, которые будут записаны в файл.
     */
    @Gateway
    void writeToFife(@Header(FileHeaders.FILENAME) String filename, String data);
}

