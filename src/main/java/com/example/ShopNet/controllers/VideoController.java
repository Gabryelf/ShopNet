package com.example.ShopNet.controllers;

import com.example.ShopNet.models.Product;
import com.example.ShopNet.models.Video;
import com.example.ShopNet.services.ProductService;
import com.example.ShopNet.services.VideoService;
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

@Controller
@Slf4j
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final ProductService productService;

    @GetMapping("/upload/{productName}")
    public String showUploadForm(@PathVariable String productName, Model model) {
        List<Product> products = productService.findByTitle(productName);
        if (!products.isEmpty()) {
            Product product = products.get(0); // Получаем первый найденный продукт
            model.addAttribute("product", product);
            model.addAttribute("video", new Video());
            return "uploadForm";
        } else {
            // Если продукт с указанным productName не найден, перенаправляем на главную страницу
            log.info( " ErrorDeleted. NotFoundName{}", videoService.findByProductName( productName ));
            return "redirect:/";
        }
    }


    @PostMapping("/upload/{productName}")
    public String uploadVideo(@PathVariable String productName,
                              @ModelAttribute("video") Video video,
                              @RequestParam("videoFile") MultipartFile videoFile,
                              RedirectAttributes redirectAttributes) {
        try {
            video.setName(videoFile.getOriginalFilename());
            video.setVideoData(videoFile.getBytes());
            videoService.saveVideo(video, productName);
            redirectAttributes.addFlashAttribute("message", "Video uploaded successfully!");
        } catch (IOException e) {
            // В случае ошибки загрузки видео, сообщаем пользователю и перенаправляем на страницу загрузки
            redirectAttributes.addFlashAttribute("message", "Failed to upload video!");
            return "redirect:/video/upload/" + productName;
        }
        // После успешной загрузки видео перенаправляем на страницу с деталями продукта
        return "redirect:/product/upload/" + productName;
    }


    @GetMapping("/delete/{productName}")
    public String showDeleteForm(@PathVariable String productName, Model model) {
        // Найти видео по названию товара
        Video video = videoService.findByProductName(productName);
        if (video != null) {
            model.addAttribute("video", video);
            return "deleteForm";
        } else {
            // Если видео не найдено, выполнить какие-то действия, например, перенаправить на главную страницу
            return "redirect:/product-info";
        }
    }


    @PostMapping("/delete/{productName}")
    public String deleteVideo(@PathVariable String productName, RedirectAttributes redirectAttributes) {
        // Убедимся, что productName не пустой
        if (productName != null && !productName.isEmpty()) {
            // Получаем видео по названию продукта
            Video video = videoService.findByProductName(productName);

            // Проверяем, найдено ли видео
            if (video != null) {
                // Удаляем видео
                videoService.deleteVideo(video.getId());
                // Устанавливаем сообщение об успешном удалении
                redirectAttributes.addFlashAttribute("message", "Video deleted successfully!");
                // Перенаправляем на страницу деталей продукта
                return "redirect:/product/details/" + productName;
            } else {
                // Если видео не найдено, перенаправляем на главную страницу
                return "redirect:/";
            }
        } else {
            // Если productName пустой или null, перенаправляем на главную страницу
            return "redirect:/";
        }
    }

    @GetMapping("/download/{productName}")
    public ResponseEntity<byte[]> downloadVideo(@PathVariable("productName") String productName) {
        // Получить видео файл из базы данных для продукта с указанным productName
        Video video = videoService.findByProductName(productName);

        if (video != null && video.getVideoData() != null) {
            // Если видео файл найден, вернуть его содержимое как массив байтов с соответствующими заголовками
            return ResponseEntity
                    .ok()
                    .contentType( MediaType.APPLICATION_OCTET_STREAM)
                    .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getName() + "\"")
                    .body(video.getVideoData());
        } else {
            // Если видео файл не найден или его содержимое пустое, вернуть ResponseEntity с кодом ошибки
            return ResponseEntity.notFound().build();
        }
    }


}






