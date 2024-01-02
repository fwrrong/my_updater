package com.fwrrong.my_updater.controller;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductServiceController {
    private ProductService productService;

//    @GetMapping("/products/")
//    public ResponseEntity<List<Product>> getProducts(){
////        add validation and error handling here
//        List<Product> productList = new ArrayList<>();
//        return ResponseEntity.ok(productList);
//    }
//
//    @GetMapping("/product/{productName}/")
//    public ResponseEntity<List<Product>> searchProducts(String productName){
////        search for productName, and return a list of product (rank by relevance of user's input name)
//        List<Product> productList = new ArrayList<>();
//        return ResponseEntity.ok(productList);
//    }
//
//
//    @PostMapping("/product/{productId}/")
//    public void subscribeProducts(int productId){
////        in database, record user XX subscribed productId
//
//    }

    @GetMapping("/v1/product/{product_uuid}")
    public ResponseEntity<Product> getProduct(@PathVariable String product_uuid){
//        Get one product from product_uuid
//        GET /v1/product/{product_uuid}
//        response: Product object (name, UUID, image, link)
//        status: 200 ok
        UUID uuid = UUID.fromString(product_uuid);
        Product product = productService.getProduct(uuid);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/v1/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
//    Add one product to MongoDB
//    POST /v1/product
//    payload: Product object(...)
//    response: Product
//    status: 201
        Product savedProduct = productService.postProduct(product);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedProduct.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping(" /v1/product/{product_uuid}")
    public ResponseEntity<Product> modifyProduct(@RequestBody Product product, @PathVariable String product_uuid){
//        Modify one product （stock. etc)
//        PUT /v1/product/{product_uuid}
//        payload: Product object
//        response: Product
//        status: 200
        UUID uuid = UUID.fromString(product_uuid);
        Product updatedProduct = productService.putProduct(uuid, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/v1/product/{product_uuid}")
    public ResponseEntity<?> deleteProduct(@PathVariable String product_uuid){
//    Delete one product
//    DELETE /v1/product/{product_uuid}
//    response: success/failure
//    status: 204
        UUID uuid = UUID.fromString(product_uuid);
        productService.deleteProduct(uuid);
        return ResponseEntity.noContent().build();
    }




}
