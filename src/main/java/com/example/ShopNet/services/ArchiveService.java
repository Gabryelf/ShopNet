package com.example.ShopNet.services;

import com.example.ShopNet.models.Archive;
import com.example.ShopNet.models.Product;
import com.example.ShopNet.repositories.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;

    public void saveArchive(Archive archive, String productName) throws IOException {

        archive.setProductName(productName);
        archiveRepository.save(archive);
    }

    public void deleteArchive(Long archiveId) {

        archiveRepository.deleteById(archiveId);
    }

    public Archive findById(Long archiveId) {

        return archiveRepository.findById(archiveId).orElse(null);
    }

    public byte[] getArchiveData(Long archiveId) {
        Archive archive = archiveRepository.findById(archiveId).orElse(null);
        if (archive != null) {
            return archive.getArchiveData();
        } else {

            return null;
        }
    }


    public Archive findByProductName(String productName) {
        return archiveRepository.findByProductName(productName);
    }


    public List<Archive> findAllArchives() {
        return archiveRepository.findAll();
    }
}
