package com.example.ShopNet.configurations;

import com.example.ShopNet.controllers.UnityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class ProjectInfoFileManager {

    private static final Logger logger = LoggerFactory.getLogger( UnityController.class);
    private static final String PROJECT_INFO_FILE = "index_path.json";

    public void saveProjectInfoToJson(UnityProjectInfo projectInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(PROJECT_INFO_FILE), projectInfo);
            logger.info("Информация о проекте успешно сохранена в файл: {}", PROJECT_INFO_FILE);
        } catch (IOException e) {
            logger.error("Ошибка при сохранении информации о проекте в файл", e);
        }
    }
}

