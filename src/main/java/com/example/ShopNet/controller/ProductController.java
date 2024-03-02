package com.example.ShopNet.controller;

import com.example.ShopNet.model.Product;
import com.example.ShopNet.service.ProductService;
import freemarker.ext.beans.StringModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model){
        model.addAttribute("products", productService.listProducts(title));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);

        if (product == null) {
            return "redirect:/";
        }

        model.addAttribute("product", product);
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(Product product){
        productService.saveProduct( product );
        return "redirect:/";

    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        productService.deleteProduct(id);
        return "redirect:/";  // Замените на return "products";
    }




}
