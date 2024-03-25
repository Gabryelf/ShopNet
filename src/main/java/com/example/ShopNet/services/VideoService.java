package com.example.ShopNet.services;

import com.example.ShopNet.models.Video;
import com.example.ShopNet.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Сервис для работы с видеофайлами.
 * Обеспечивает операции сохранения, удаления и поиска видеофайлов по различным критериям.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    /**
     * Сохраняет видеофайл с указанным названием продукта.
     *
     * @param video       Видеофайл для сохранения.
     * @param productName Название продукта, к которому относится видео.
     * @throws IOException Если произошла ошибка ввода-вывода при сохранении видео.
     */

    public void saveVideo(Video video, String productName) throws IOException {
        // Устанавливаем productName для видео и сохраняем его
        video.setProductName(productName);
        videoRepository.save(video);
    }

    /**
     * Удаляет видеофайл по его идентификатору.
     *
     * @param videoId Идентификатор видеофайла для удаления.
     */

    public void deleteVideo(Long videoId) {
        // Удаляем видео по его идентификатору
        videoRepository.deleteById(videoId);
    }

    /**
     * Находит видеофайл по его идентификатору.
     *
     * @param videoId Идентификатор видеофайла для поиска.
     * @return Найденный видеофайл или null, если не найден.
     */
    public Video findById(Long videoId) {
        // Находим видео по его идентификатору
        return videoRepository.findById(videoId).orElse(null);
    }

    /**
     * Находит видеофайл по названию продукта.
     *
     * @param productName Название продукта, к которому относится видео.
     * @return Найденный видеофайл или null, если не найден.
     */

    public Video findByProductName(String productName) {
        return videoRepository.findByProductName(productName);
    }
}





