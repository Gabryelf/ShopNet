package com.example.ShopNet.repositories;


import com.example.ShopNet.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для взаимодействия с сущностью Video в базе данных.
 */

public interface VideoRepository extends JpaRepository<Video, Long> {

    /**
     * Находит видео по имени продукта.
     *
     * @param productName Название продукта, к которому привязано видео.
     * @return Видео, связанное с указанным названием продукта.
     */
    Video findByProductName(String productName);
}


