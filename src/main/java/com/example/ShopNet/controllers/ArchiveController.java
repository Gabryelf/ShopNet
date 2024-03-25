package com.example.ShopNet.controllers;

import com.example.ShopNet.models.Archive;
import com.example.ShopNet.models.Product;
import com.example.ShopNet.services.ArchiveService;
import com.example.ShopNet.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

/**
 * Контроллер для работы с архивами.
 */

@Controller
@Slf4j
@RequestMapping("/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;
    private final ProductService productService;

    /**
     * Отображает форму загрузки архива для указанного продукта.
     * Если архив уже существует для данного продукта, добавляет информацию об этом в модель.
     *
     * @param productName Название продукта.
     * @param model       Модель для передачи данных в представление.
     * @return Строку, представляющую имя представления "upArcForm".
     */
    @GetMapping("/up-archive/{productName}")
    public String showUpArchiveForm(@PathVariable String productName, Model model) {
        List<Product> products = productService.findByTitle(productName);
        if (!products.isEmpty()) {
            Product product = products.get(0);
            model.addAttribute("product", product);

            // Проверяем наличие архива для данного продукта
            Archive existingArchive = archiveService.findByProductName(productName);
            if (existingArchive != null) {
                // Если архив уже существует, добавляем сообщение об этом в модель
                model.addAttribute("archiveExists", true);
                model.addAttribute("archiveName", existingArchive.getName());
                model.addAttribute("archiveId", existingArchive.getId());
            } else {
                // Если архив не существует, добавляем новый пустой объект архива в модель
                model.addAttribute("archiveExists", false);
                model.addAttribute("archive", new Archive());
            }

            return "upArcForm";
        } else {
            // Если продукт с указанным productName не найден, перенаправляем на главную страницу
            log.info("ErrorDeleted. NotFoundName{}", archiveService.findByProductName(productName));
            return "redirect:/";
        }
    }


    /**
     * Загружает архив для указанного продукта.
     *
     * @param productName        Название продукта.
     * @param archive            Объект архива.
     * @param archiveFile        Файл архива для загрузки.
     * @param redirectAttributes Объект для добавления атрибутов для перенаправления.
     * @return Строку перенаправления на страницу деталей продукта.
     */


    @PostMapping("/up-archive/{productName}")
    public String uploadArchive(@PathVariable String productName,
                              @ModelAttribute("archive") Archive archive,
                              @RequestParam("archiveFile") MultipartFile archiveFile,
                              RedirectAttributes redirectAttributes) {
        try {
            archive.setName(archiveFile.getOriginalFilename());
            archive.setArchiveData(archiveFile.getBytes());
            archiveService.saveArchive(archive, productName);
            redirectAttributes.addFlashAttribute("message", "Archive uploaded successfully!");
        } catch (IOException e) {
            // В случае ошибки загрузки видео, сообщаем пользователю и перенаправляем на страницу загрузки
            redirectAttributes.addFlashAttribute("message", "Failed to upload archive!");
            return "redirect:/archive/upload/" + productName;
        }
        // После успешной загрузки видео перенаправляем на страницу с деталями продукта
        return "redirect:/product/upload/" + productName;
    }

    /**
     * Отображает форму удаления архива для указанного продукта.
     * Если архив не найден, перенаправляет на главную страницу.
     *
     * @param productName Название продукта.
     * @param model       Модель для передачи данных в представление.
     * @return Строку, представляющую имя представления "delArcForm" при успешном поиске архива,
     * иначе перенаправляет на главную страницу.
     */

    @GetMapping("/del-archive/{productName}")
    public String showDeleteArchive(@PathVariable String productName, Model model) {
        // Найти archive по названию товара
        Archive archive = archiveService.findByProductName(productName);
        if (archive != null) {
            model.addAttribute("archive", archive);
            return "delArcForm";
        } else {
            // Если archive не найден, выполнить какие-то действия, например, перенаправить на главную страницу
            return "redirect:/product-info";
        }
    }


    /**
     * Удаляет архив для указанного продукта.
     * После успешного удаления архива устанавливает сообщение об успешном удалении и перенаправляет на страницу деталей продукта.
     *
     * @param productName        Название продукта.
     * @param redirectAttributes Объект для добавления атрибутов для перенаправления.
     * @return Строку перенаправления на страницу деталей продукта при успешном удалении архива,
     * иначе перенаправляет на главную страницу.
     */
    @PostMapping("/del-archive/{productName}")
    public String deleteArchive(@PathVariable String productName, RedirectAttributes redirectAttributes) {
        // Убедимся, что productName не пустой
        if (productName != null && !productName.isEmpty()) {
            // Получаем archive по названию продукта
            Archive archive = archiveService.findByProductName(productName);

            if (archive != null) {
                // Удаляем видео
                archiveService.deleteArchive(archive.getId());
                // Устанавливаем сообщение об успешном удалении
                redirectAttributes.addFlashAttribute("message", "Archive deleted successfully!");
                // Перенаправляем на страницу деталей продукта
                return "redirect:/product/details/" + productName;
            } else {

                return "redirect:/";
            }
        } else {
            // Если productName пустой или null, перенаправляем на главную страницу
            return "redirect:/";
        }
    }

    /**
     * Скачивает архив для указанного продукта.
     * Если архив не найден, возвращает ResponseEntity с кодом ошибки.
     *
     * @param productName Название продукта.
     * @return ResponseEntity с содержимым архива или кодом ошибки, если архив не найден.
     */

    @GetMapping("/download/{productName}")
    public ResponseEntity<byte[]> downloadArchive(@PathVariable("productName") String productName) {
        log.info("Downloading archive for product: {}", productName);

        // Получить архив из базы данных по ключу productName
        Archive archive = archiveService.findByProductName(productName);

        if (archive != null && archive.getArchiveData() != null) {
            // Если архив найден, вернуть его содержимое как массив байтов с соответствующими заголовками
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archive.getName() + "\"")
                    .body(archive.getArchiveData());
        } else {
            // Если архив не найден, вернуть ResponseEntity с кодом ошибки
            log.warn("Archive not found for product: {}", productName);
            return ResponseEntity.notFound().build();
        }
    }

}