package com.fwrrong.my_updater.controller;
import com.fwrrong.my_updater.exception.AddProductException;
import com.fwrrong.my_updater.exception.DeleteProductException;
import com.fwrrong.my_updater.exception.GetProductException;
import com.fwrrong.my_updater.exception.ModifyUserException;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.service.ProductService;
import com.fwrrong.my_updater.service.ValidationService;
import org.apache.coyote.Response;
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

//    @GetMapping("/products/")
//    public ResponseEntity<List<Product>> getProducts(){
////        add validation and error handling here
//        // TODO: add cache, use @Cacheable
//        List<Product> productList = productService.getFeaturedProduct();
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
    public ResponseEntity<?> getProduct(@PathVariable("product_uuid") String productId){
//        Get one product from product_uuid
//        GET /v1/product/{product_uuid}
//        response: Product object (name, UUID, image, link)
//        status: 200 ok
        UUID uuid;
        Product product;
        try {
            uuid = UUID.fromString(productId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        if (!validationService.validateProductId(uuid)) {
            return ResponseEntity.badRequest().body("product id not found");
        }

        try {
            product = productService.getProduct(uuid);
        } catch (GetProductException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(product);
    }

//    @GetMapping("/v1/product/name/{product_name}")
//    public ResponseEntity<Product> getProductByName(@PathVariable("product_name") String productName) {
//        var p = productService.getProductByName(productName);
//        return ResponseEntity.ok(p);
//    }

//    @PostMapping("/v1/product")
//    public ResponseEntity<?> addProduct(@RequestBody Map<String, String> requestBody) {
////    Add one product to MongoDB
////    POST /v1/product
////    payload:
////        {
////            "name": "Product Name 6",
////            "image": "Image URL 6",
////            "link": "Link URL 6"
////        }
//////    response: Product
////    status: 201
//        // TODO validation if a valid product
//        String name = requestBody.get("name");
//        String image = requestBody.get("image");
//        String url = requestBody.get("url");
//        String size = requestBody.get("size");
//
//        Product savedProduct;
//        try {
//            savedProduct = productService.addProduct(name, image, url, size);
//        } catch (AddProductException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
//    }

    @PutMapping("/v1/product/{product_uuid}")
    public ResponseEntity<?> modifyProduct(@RequestBody Map<String, String> requestBody, @PathVariable("product_uuid") String productUuid) {
//        Modify one product （stock. etc)
//        PUT /v1/product/{product_uuid}
//        payload:
//        {
//            "name": "Product Name 6",
//            "image": "Image URL 6",
//            "url": "Link URL 6"
//        }
//        response: Product
//        status: 200
//        TODO: validation
        UUID uuid;
        String name = requestBody.get("name");
        String image = requestBody.get("image");
        String url = requestBody.get("url");
        String size = requestBody.get("size");
        Boolean inStock = Boolean.parseBoolean(requestBody.get("in_stock"));
        Product updatedProduct;

        try {
            uuid = UUID.fromString(productUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid product UUID");
        }

        if (!validationService.validateProductId(uuid)) {
            return ResponseEntity.badRequest().body("product id not found");
        }

        if (!validationService.validateProductName(name)) {
            return ResponseEntity.badRequest().body("Invalid product name");
        }

        if (!validationService.validateProductImage(image)) {
            return ResponseEntity.badRequest().body("Invalid product image");
        }

        if(!validationService.validateProductUrl(url)){
            return ResponseEntity.badRequest().body("Invalid product url");
        }

        if (!validationService.validateProductInStock(inStock)) {
            return ResponseEntity.badRequest().body("Invalid product in_stock");
        }

        if (!validationService.validateProductSize(size)) {
            return ResponseEntity.badRequest().body("Invalid product size");
        }

        try {
            updatedProduct = productService.modifyProduct(uuid, name, inStock, image, url, size);
        } catch (ModifyUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/v1/product/{product_uuid}")
    public ResponseEntity<?> deleteProduct(@PathVariable("product_uuid") String productId){
//    Delete one product
//    DELETE /v1/product/{product_uuid}
//    response: success/failure
//    status: 204
        UUID uuid;

        try {
            uuid = UUID.fromString(productId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        if (!validationService.validateProductId(uuid)) {
            return ResponseEntity.badRequest().body("Product id not found");
        }

        try {
            productService.deleteProduct(uuid);
        } catch (DeleteProductException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Product deleted successfully");
    }
}
