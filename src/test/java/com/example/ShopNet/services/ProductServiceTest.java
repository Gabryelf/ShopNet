package com.example.ShopNet.services;

import com.example.ShopNet.models.Product;
import com.example.ShopNet.models.User;
import com.example.ShopNet.repositories.ProductRepository;
import com.example.ShopNet.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testListProductsWithTitle() {
        String title = "SampleTitle";
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());

        Mockito.when(productRepository.findByTitle(title)).thenReturn(expectedProducts);

        List<Product> result = productService.listProducts(title);

        assertEquals(expectedProducts, result);
    }

    @Test
    public void testListProductsWithoutTitle() {
        List<Product> expectedProducts = Arrays.asList(new Product(), new Product());

        Mockito.when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> result = productService.listProducts(null);

        assertEquals(expectedProducts, result);
    }

    public void testSaveProduct() throws IOException {
        Principal principal = Mockito.mock(Principal.class);
        User user = new User();
        Product product = new Product();
        MultipartFile file1 = Mockito.mock(MultipartFile.class);
        MultipartFile file2 = Mockito.mock(MultipartFile.class);
        MultipartFile file3 = Mockito.mock(MultipartFile.class);

        Mockito.when(principal.getName()).thenReturn("user@example.com");
        Mockito.when(userRepository.findByEmail("user@example.com")).thenReturn(user);

        productService.saveProduct(principal, product, file1, file2, file3);


    }

    @Test
    public void testDeleteProduct() {
        User user = new User();
        user.setId(1L);
        Product product = new Product();
        product.setId(2L);
        product.setUser(user);

        Mockito.when(productRepository.findById(2L)).thenReturn( Optional.of(product));

        productService.deleteProduct(user, 2L);

        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
}
