package com.example.ShopNet.repositories;

import com.example.ShopNet.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для взаимодействия с сущностью Image в базе данных.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
