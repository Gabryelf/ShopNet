package com.example.ShopNet.controllers;

import com.example.ShopNet.configurations.ArchiveExtractor;
import com.example.ShopNet.configurations.UnityProjectInfo;
import com.example.ShopNet.models.Archive;
import com.example.ShopNet.services.ArchiveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/unity")
public class UnityController {

    private static final Logger logger = LoggerFactory.getLogger(UnityController.class);
    private static final String PROJECT_INFO_FILE = "index_path.json";

    private final ArchiveService archiveService;
    private final ArchiveExtractor archiveExtractor;

    @GetMapping("/startArchive/{productName}")
    public synchronized ResponseEntity<String> startUnityProject(@PathVariable("productName") String productName) {
        try {
            logger.info("Начало обработки запроса startUnityProject для productName: {}", productName);

            // Проверяем, существует ли информация о проекте в файле index_path.json
            UnityProjectInfo projectInfo = readProjectInfoFromJson();
            if (projectInfo != null && projectInfo.getProjectName().equals(productName)) {
                // Если информация о проекте уже существует и совпадает с запрошенным проектом,
                // то просто возвращаем путь к проекту без повторного копирования архива
                String unityProjectUrl = "C:/Windows/unity/" + productName + "/index.html";
                logger.info("Проект Unity уже скачан. URL проекта Unity: {}", unityProjectUrl);
                return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
                        .body("<html><body><script>window.location.href = \"" + unityProjectUrl + "\";</script></body></html>");
            }

            // Получаем архив из базы данных
            Archive archive = archiveService.findByProductName(productName);
            if (archive == null) {
                logger.warn("Архив с именем {} не найден в базе данных", productName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Архив с указанным именем не найден");
            }

            // Создаем директорию для проекта Unity
            String projectDirectory = "C:/Windows/unity/" + productName.replace(".zip", "") + "/";
            Path directoryPath = Path.of(projectDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                logger.info("Создана директория для проекта Unity: {}", directoryPath);
            }

            // Извлекаем и перемещаем файлы из архива в директорию проекта Unity
            try {
                archiveExtractor.extractArchive(archive.getArchiveData(), projectDirectory);
            } catch (IOException e) {
                logger.error("Ошибка при извлечении и перемещении файлов из архива", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Ошибка при извлечении и перемещении файлов из архива: " + e.getMessage());
            }

            // Сохраняем информацию о проекте в файл index_path.json
            saveProjectInfoToJson(productName, projectDirectory);

            // Возвращаем HTML страницу с JavaScript для автоматического перенаправления на URL проекта Unity
            String unityProjectUrl = "C:/Windows/unity/" + productName + "/index.html";
            String htmlResponse = "<html><body><script>window.location.href = \"" + unityProjectUrl + "\";</script></body></html>";
            return ResponseEntity.ok().contentType( MediaType.TEXT_HTML).body(htmlResponse);
        } catch (IOException e) {
            // В случае ошибки ввода-вывода при обработке архива, возвращаем ошибку сервера
            logger.error("Ошибка при обработке архива", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при обработке архива: " + e.getMessage());
        } finally {
            logger.info("Завершение обработки запроса startUnityProject для productName: {}", productName);
        }
    }



    private UnityProjectInfo readProjectInfoFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(PROJECT_INFO_FILE);

        if (!file.exists()) {
            return null;
        }

        try {
            UnityProjectInfo projectInfo = objectMapper.readValue(file, UnityProjectInfo.class);
            logger.info("Информация о проекте успешно прочитана из файла: {}", PROJECT_INFO_FILE);
            return projectInfo;
        } catch (IOException e) {
            logger.error("Ошибка при чтении информации о проекте из файла", e);
            return null;
        }
    }

    public void saveProjectInfoToJson(String productName, String projectDirectory) {
        UnityProjectInfo projectInfo = new UnityProjectInfo(productName, projectDirectory);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(PROJECT_INFO_FILE), projectInfo);
            logger.info("Информация о проекте успешно сохранена в файл: {}", PROJECT_INFO_FILE);
        } catch (IOException e) {
            logger.error("Ошибка при сохранении информации о проекте в файл", e);
        }
    }
}









/*@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/unity")
public class UnityController {

    private static final Logger logger = LoggerFactory.getLogger( UnityController.class );
    private final ArchiveExtractor archiveExtractor;


    @PostMapping("/startArchive")
    public synchronized ResponseEntity<String> startUnityProject(@RequestParam("archiveFile") MultipartFile archiveFile,
                                                                 @RequestParam("productName") String productName) {
        try {
            logger.info("Начало обработки запроса startUnityProject");

            // Проверяем, был ли загружен файл архива
            if (archiveFile == null || archiveFile.isEmpty()) {
                logger.warn("Файл архива не был загружен");
                return ResponseEntity.badRequest().body("Файл архива не был загружен");
            }

            logger.info("Файл архива был успешно загружен");

            // Получаем содержимое загруженного файла
            byte[] archiveData = archiveFile.getBytes();

            // Получаем директорию, куда был загружен файл
            String uploadDirectory = "C:/Windows/unity"; // Уточните этот путь
            logger.info("Директория загрузки: {}", uploadDirectory);

            // Разархивируем архив и получаем название проекта
            String projectName = archiveExtractor.extractArchive(archiveData, productName);

            if (projectName == null || projectName.isEmpty()) {
                logger.warn("Не удалось определить название Unity-проекта");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось определить название Unity-проекта");
            }

            logger.info("Название проекта: {}", projectName);

            // Формируем путь к проекту Unity
            String projectDirectory = uploadDirectory + projectName + "/";
            logger.info("Путь к проекту Unity: {}", projectDirectory);

            // Создаем директорию, если она не существует
            Path directoryPath = Path.of(projectDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                logger.info("Создана директория: {}", directoryPath);
            }

            // Перемещаем файл архива в директорию проекта
            Path filePath = Path.of(projectDirectory + archiveFile.getOriginalFilename());
            Files.copy(archiveFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Файл архива успешно перемещен в директорию проекта");

            // Формируем URL проекта Unity
            String unityProjectUrl = projectDirectory + "index.html";
            logger.info("URL проекта Unity: {}", unityProjectUrl);

            // Возвращаем URL проекта Unity
            return ResponseEntity.ok(unityProjectUrl);
        } catch (IOException e) {
            // В случае ошибки ввода-вывода при обработке файла архива, возвращаем ошибку сервера
            logger.error("Ошибка при обработке файла архива", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при обработке файла архива: " + e.getMessage());
        } finally {
            logger.info("Завершение обработки запроса startUnityProject");
        }
    }


}*/



