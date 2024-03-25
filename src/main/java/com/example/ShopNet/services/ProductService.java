package com.example.ShopNet.services;

import com.example.ShopNet.models.Image;
import com.example.ShopNet.models.Product;
import com.example.ShopNet.models.User;
import com.example.ShopNet.repositories.ProductRepository;
import com.example.ShopNet.repositories.UserRepository;
import com.example.ShopNet.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления продуктами.
 * Обеспечивает операции сохранения, удаления и получения информации о продуктах.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    private List<Product> products = new ArrayList<>();

    /**
     * Возвращает список продуктов, удовлетворяющих указанному названию.
     *
     * @param title Название продукта.
     * @return Список продуктов с указанным названием.
     */

    public List<Product> listProducts(String title) {
        if (title != null) return productRepository.findByTitle( title );
        return productRepository.findAll();
    }
    /**
     * Сохраняет новый продукт.
     *
     * @param principal Аутентифицированный пользователь.
     * @param product   Продукт для сохранения.
     * @param file1     Первый файл изображения.
     * @param file2     Второй файл изображения.
     * @param file3     Третий файл изображения.
     * @throws IOException В случае ошибки ввода-вывода при обработке файлов изображений.
     */


    public void saveProduct( Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity( file1 );
            image1.setPreviewImage( true );
            product.addImageToProduct( image1 );
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity( file2 );
            product.addImageToProduct( image2 );
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity( file3 );
            product.addImageToProduct( image3 );
        }
        log.info( "Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail() );
        Product productFromDb = productRepository.save( product );
        productFromDb.setPreviewImageId( productFromDb.getImages().get( 0 ).getId() );
        productRepository.save( product );
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return Список всех продуктов.
     */

    public List<Product> getAllProducts() {
        // Получение всех товаров из репозитория
        return productRepository.findAll();
    }

    /**
     * Возвращает пользователя по его принципалу.
     *
     * @param principal Принципал пользователя.
     * @return Пользователь, соответствующий принципалу.
     */
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    /**
     * Преобразует файл изображения в сущность изображения.
     *
     * @param file Файл изображения для преобразования.
     * @return Сущность изображения.
     * @throws IOException В случае ошибки ввода-вывода при обработке файла изображения.
     */
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName( file.getName() );
        image.setOriginalFileName( file.getOriginalFilename() );
        image.setContentType( file.getContentType() );
        image.setSize( file.getSize() );
        image.setBytes( file.getBytes() );
        return image;
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     */
    public void deleteProduct( Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {

            productRepository.delete(product);

            log.info("Product with id = {} was deleted", id);
            // if (product.getUser().getId().equals(user.getId())) {
            //    productRepository.delete(product);

            //    log.info("Product with id = {} was deleted", id);
            //} else {
            //    log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            //}
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }

    /**
     * Возвращает продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Продукт с указанным идентификатором.
     */


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Возвращает список продуктов по их названию.
     *
     * @param productName Название продукта.
     * @return Список продуктов с указанным названием.
     */

    public List<Product> findByTitle(String productName) {
        return productRepository.findByTitle( productName );
    }
}
