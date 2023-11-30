package com.fwrrong.my_updater.controller;
import com.fwrrong.my_updater.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductServiceController {

    @GetMapping("/products/")
    public ResponseEntity<List<Product>> getProducts(){
//        add validation and error handling here
        List<Product> productList = new ArrayList<>();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/product/{productName}/")
    public ResponseEntity<List<Product>> searchProducts(String productName){
//        search for productName, and return a list of product (rank by relevance of user's input name)
        List<Product> productList = new ArrayList<>();
        return ResponseEntity.ok(productList);
    }


    @PostMapping("/product/{productId}/")
    public void subscribeProducts(int productId){
//        in database, record user XX subscribed productId

    }
}
