package com.example.ShopNet.controller;

import com.example.ShopNet.model.Product;
import com.example.ShopNet.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String products(Model model){
        model.addAttribute("products", productService.listProducts());
        return "products";
    }

    /*@GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model){

        model.addAttribute("products", productService.getProductById(id));
        return "product-info";

    }*/
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

    /*@PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }*/

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        productService.deleteProduct(id);
        return "redirect:/";
    }



}
