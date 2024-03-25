package com.example.ShopNet.repositories;

import com.example.ShopNet.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с сущностью Product в базе данных.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Находит список продуктов по их заголовку.
     *
     * @param title Заголовок продукта для поиска.
     * @return Список продуктов с указанным заголовком.
     */
    List<Product> findByTitle(String title);



}
