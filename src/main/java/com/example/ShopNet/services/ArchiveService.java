package com.example.ShopNet.services;

import com.example.ShopNet.models.Archive;
import com.example.ShopNet.repositories.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Сервис для работы с архивами.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    /**
     * Сохраняет архив в базе данных.
     *
     * @param archive      Архив для сохранения.
     * @param productName  Название продукта, к которому привязан архив.
     * @throws IOException Если происходит ошибка ввода-вывода при сохранении архива.
     */

    public void saveArchive(Archive archive, String productName) throws IOException {

        archive.setProductName(productName);
        archiveRepository.save(archive);
    }

    /**
     * Удаляет архив из базы данных по его идентификатору.
     *
     * @param archiveId Идентификатор архива для удаления.
     */
    public void deleteArchive(Long archiveId) {

        archiveRepository.deleteById(archiveId);
    }
    /**
     * Находит архив в базе данных по его идентификатору.
     *
     * @param archiveId Идентификатор архива.
     * @return Архив, если он найден, в противном случае - null.
     */

    public Archive findById(Long archiveId) {

        return archiveRepository.findById(archiveId).orElse(null);
    }
    /**
     * Получает данные архива из базы данных по его идентификатору.
     *
     * @param archiveId Идентификатор архива.
     * @return Данные архива в виде массива байтов, если архив найден, в противном случае - null.
     */

    public byte[] getArchiveData(Long archiveId) {
        Archive archive = archiveRepository.findById(archiveId).orElse(null);
        if (archive != null) {
            return archive.getArchiveData();
        } else {

            return null;
        }
    }
    /**
     * Находит архив по названию продукта.
     *
     * @param productName Название продукта.
     * @return Архив, связанный с указанным названием продукта.
     */


    public Archive findByProductName(String productName) {
        return archiveRepository.findByProductName(productName);
    }

    /**
     * Получает список всех архивов из базы данных.
     *
     * @return Список всех архивов.
     */

    public List<Archive> findAllArchives() {
        return archiveRepository.findAll();
    }
}
