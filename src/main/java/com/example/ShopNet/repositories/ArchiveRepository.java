package com.example.ShopNet.repositories;

import com.example.ShopNet.models.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {

    Archive findByProductName(String productName);


}
