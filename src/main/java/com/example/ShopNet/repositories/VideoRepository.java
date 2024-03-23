package com.example.ShopNet.repositories;


import com.example.ShopNet.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findByProductName(String productName);
}


