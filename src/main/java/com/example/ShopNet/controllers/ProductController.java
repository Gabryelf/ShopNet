package com.example.ShopNet.controllers;

import com.example.ShopNet.models.Product;
import com.example.ShopNet.models.User;
import com.example.ShopNet.models.Video;
import com.example.ShopNet.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с продуктами.
 */
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Отображает страницу со списком продуктов.
     *
     * @param title     Название продукта (необязательный параметр).
     * @param principal Информация о текущем пользователе.
     * @param model     Модель для передачи данных в представление.
     * @return Путь к странице со списком продуктов.
     */
    @GetMapping("/products")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model){
        model.addAttribute("products", productService.listProducts(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }

    /**
     * Отображает информацию о продукте.
     *
     * @param id    Идентификатор продукта.
     * @param model Модель для передачи данных в представление.
     * @return Путь к странице с информацией о продукте.
     */

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute( "product", product );
        model.addAttribute( "images", product.getImages() );

        return "product-info";
    }

    /**
     * Создает новый продукт.
     *
     * @param file1     Первое изображение продукта.
     * @param file2     Второе изображение продукта.
     * @param file3     Третье изображение продукта.
     * @param product   Данные о новом продукте.
     * @param principal Информация о текущем пользователе.
     * @return Путь для перенаправления после создания продукта.
     * @throws IOException Исключение, возникающее при работе с файлами.
     */


    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal,product, file1, file2, file3);
        return "redirect:/my/products";
    }

    /**
     * Удаляет продукт.
     *
     * @param id Идентификатор удаляемого продукта.
     * @return Путь для перенаправления после удаления продукта.
     */

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/";
    }

    /**
     * Отображает страницу со списком продуктов пользователя.
     *
     * @param principal Информация о текущем пользователе.
     * @param model     Модель для передачи данных в представление.
     * @return Путь к странице с продуктами пользователя.
     */

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-products";
    }


}
