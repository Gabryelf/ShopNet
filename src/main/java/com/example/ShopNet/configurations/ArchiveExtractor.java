package com.example.ShopNet.configurations;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@Slf4j
public class ArchiveExtractor {

    public void extractArchive(byte[] archiveData, String targetDirectory) throws IOException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(archiveData);
             ZipInputStream zis = new ZipInputStream(bis)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                if (!entry.isDirectory()) {
                    // Формируем путь для файла в целевой директории
                    Path targetPath = Paths.get(targetDirectory, entryName);

                    // Создаем директории для файла, если они не существуют
                    Files.createDirectories(targetPath.getParent());

                    // Копируем файл
                    try (OutputStream os = Files.newOutputStream(targetPath)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zis.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
    }




}


