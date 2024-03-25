package com.example.ShopNet.repositories;

import com.example.ShopNet.models.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для взаимодействия с сущностью Archive в базе данных.
 */

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    /**
     * Поиск архива по названию продукта.
     *
     * @param productName название продукта
     * @return найденный архив или null, если архив не найден
     */
    Archive findByProductName(String productName);


}
