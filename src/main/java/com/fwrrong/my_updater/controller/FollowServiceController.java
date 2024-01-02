package com.fwrrong.my_updater.controller;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class FollowServiceController {
    private FollowService followService;
    @GetMapping("/v1/follow/{user_uuid}/followed-products")
    public ResponseEntity<List<Product>> getFollowedProduct(@PathVariable String user_uuid){
//        Get User's Followed Products
//        GET /v1/follow/{user_uuid}/followed-products
//        Response: List of Product objects
//        Status: 200 OK
//        Use Case: Allows a user to see all the products they are following.
        UUID uuid = UUID.fromString(user_uuid);
        List<Product> productList = followService.getFollowedProduct(uuid);
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/v1/follow/{user_uuid}/follow-product")
    public ResponseEntity<?> followProduct(@PathVariable String user_uuid, @RequestBody Map<String, String> requestBody) {
//    Follow a Product
//    Endpoint: /v1/follow/{user_uuid}/follow-product
//    Method: POST
//    Payload: { "product_uuid": "product-uuid-here" }
//    Response: Success confirmation or error message
//    Status: 200 OK (on success), 404 Not Found (user/product not found), etc.
//    Use Case: Allows a user to follow a product to receive updates or notification.
        UUID userUUID = UUID.fromString(user_uuid);
        UUID productUUID = UUID.fromString(requestBody.get("product_uuid"));
        followService.followProduct(userUUID, productUUID);
        return ResponseEntity.ok("Product followed successfully");
    }

    @DeleteMapping("/v1/follow/{follow_uuid}/unfollow-product/")
    public ResponseEntity<?> unFollowProduct(@PathVariable String follow_uuid) {
//        Unfollow a Product
//        Endpoint: /v1/follow/{user_uuid}/unfollow-product/{product_uuid}
//        Method: DELETE
//        Payload: None (the product to unfollow is specified in the URL)
//        Response: Success confirmation or error message
//        Status: 200 OK (on success), 404 Not Found (user/product not found), etc.
//        Use Case: Allows a user to unfollow a product to stop receiving updates or notifications.

        UUID followUuid = UUID.fromString(follow_uuid);
//        UUID productUUID = UUID.fromString(product_uuid);
        followService.unfollowProduct(followUuid);
        return ResponseEntity.ok("Product unfollowed successfully");
    }


}
