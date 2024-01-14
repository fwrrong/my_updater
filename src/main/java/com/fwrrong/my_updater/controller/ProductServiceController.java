package com.fwrrong.my_updater.controller;
import com.fwrrong.my_updater.exception.AddProductException;
import com.fwrrong.my_updater.exception.DeleteProductException;
import com.fwrrong.my_updater.exception.GetProductException;
import com.fwrrong.my_updater.exception.ModifyUserException;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.service.ProductService;
import com.fwrrong.my_updater.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import java.util.List;

@RestController
public class ProductServiceController {
    private ProductService productService;
    private ValidationService validationService;

    public ProductServiceController(ProductService productService, ValidationService validationService) {
        this.productService = productService;
        this.validationService = validationService;
    }

    @GetMapping("/products/")
    public ResponseEntity<List<Product>> getProducts(){
//        add validation and error handling here
        // TODO: add cache, use @Cacheable
        List<Product> productList = productService.getFeaturedProduct();
        return ResponseEntity.ok(productList);
    }
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
    public ResponseEntity<?> getProduct(@PathVariable("product_uuid") String productUuid){
//        Get one product from product_uuid
//        GET /v1/product/{product_uuid}
//        response: Product object (name, UUID, image, link)
//        status: 200 ok
        UUID uuid;
        Product product;
        try {
            uuid = UUID.fromString(productUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }
        try {
            product = productService.getProduct(uuid);
        } catch (GetProductException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/v1/product")
    public ResponseEntity<?> addProduct(@RequestBody Map<String, String> requestBody) {
//    Add one product to MongoDB
//    POST /v1/product
//    payload:
//        {
//            "name": "Product Name 6",
//            "image": "Image URL 6",
//            "link": "Link URL 6"
//        }
////    response: Product
//    status: 201
        // TODO validation if a valid product
        String name = requestBody.get("name");
        String image = requestBody.get("image");
        String link = requestBody.get("link");

        Product savedProduct;
        try {
            savedProduct = productService.addProduct(name, image, link);
        } catch (AddProductException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedProduct.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/v1/product/{product_uuid}")
    public ResponseEntity<?> modifyProduct(@RequestBody Map<String, String> requestBody, @PathVariable("product_uuid") String productUuid) {
//        Modify one product （stock. etc)
//        PUT /v1/product/{product_uuid}
//        payload:
//        {
//            "name": "Product Name 6",
//            "image": "Image URL 6",
//            "link": "Link URL 6"
//        }
//        response: Product
//        status: 200
        UUID uuid;
        String name = requestBody.get("name");
        String image = requestBody.get("image");
        String link = requestBody.get("link");
        Product updatedProduct;

        try {
            uuid = UUID.fromString(productUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid product UUID");
        }

        try {
            updatedProduct = productService.modifyProduct(uuid, name, image, link);
        } catch (ModifyUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/v1/product/{product_uuid}")
    public ResponseEntity<?> deleteProduct(@PathVariable("product_uuid") String productUuid){
//    Delete one product
//    DELETE /v1/product/{product_uuid}
//    response: success/failure
//    status: 204
        UUID uuid;

        try {
            uuid = UUID.fromString(productUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        try {
            productService.deleteProduct(uuid);
        } catch (DeleteProductException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Product deleted successfully");
    }




}
