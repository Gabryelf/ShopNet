package com.example.ShopNet.services;

import com.example.ShopNet.models.Video;
import com.example.ShopNet.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public void saveVideo(Video video, String productName) throws IOException {
        // Устанавливаем productName для видео и сохраняем его
        video.setProductName(productName);
        videoRepository.save(video);
    }

    public void deleteVideo(Long videoId) {
        // Удаляем видео по его идентификатору
        videoRepository.deleteById(videoId);
    }

    public Video findById(Long videoId) {
        // Находим видео по его идентификатору
        return videoRepository.findById(videoId).orElse(null);
    }

    public Video findByProductName(String productName) {
        return videoRepository.findByProductName(productName);
    }
}





