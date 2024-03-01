package com.example.ShopNet.service;

import com.example.ShopNet.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private long ID = 0;

    {
        products.add(new Product( ++ID,"Product1", "SomeTextDescription", 100,"City1", "User001" ));
        products.add(new Product( ++ID,"Product2", "SomeTextDescription", 200,"City2", "User002" ));
    }

    public List<Product> listProducts() { return products; }

    public void saveProduct(Product product) {
        product.setId( ++ID );
        products.add( product );
    }

    public void deleteProduct(Long id){
        products.removeIf(product -> product.getId().equals(id));
    }

    public Product getProductById(Long id) {
        for( Product product : products ){
            if(product.getId().equals( id )) return product;
        }
        return  null;
    }
}
