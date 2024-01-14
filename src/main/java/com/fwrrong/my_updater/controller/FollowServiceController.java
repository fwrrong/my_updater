package com.fwrrong.my_updater.controller;

import com.fwrrong.my_updater.exception.FollowProductException;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/follow")
public class FollowServiceController {
    private FollowService followService;

    public FollowServiceController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/{user_uuid}")
    public ResponseEntity<?> getFollowedProduct(@PathVariable("user_uuid") String userUuid){
//        Get User's Followed Products
//        GET /v1/follow/{user_uuid}/followed-products
//        Response: List of Product objects
//        Status: 200 OK
//        Use Case: Allows a user to see all the products they are following.
        UUID uuid ;
        List<Product> productList;

        try {
            uuid = UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        try{
            productList = followService.getFollowedProduct(uuid);
        } catch (FollowProductException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(productList);
    }

    @PostMapping
    public ResponseEntity<?> followProduct(@RequestBody Map<String, String> requestBody) {
//    Follow a Product
//    Endpoint: /v1/follow/{user_uuid}/follow-product
//    Method: POST
//    Payload: {
//              "product_uuid": "product-uuid-here",
//              "user_uuid": "user-uuid-here"
//              }
//    Response: Success confirmation or error message
//    Status: 200 OK (on success), 404 Not Found (user/product not found), etc.
//    Use Case: Allows a user to follow a product to receive updates or notification.
        UUID userUUID;
        UUID productUUID;

        try {
            userUUID = UUID.fromString(requestBody.get("user_uuid"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid user UUID");
        }

        try {
            productUUID = UUID.fromString(requestBody.get("product_uuid"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid product UUID");
        }

        try{
            followService.followProduct(userUUID, productUUID);
        } catch (FollowProductException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Product followed successfully");
    }

    @DeleteMapping("/{user_uuid}/{product_uuid}")
    public ResponseEntity<?> unFollowProduct(@PathVariable("user_uuid") String userUuid, @PathVariable("product_uuid") String productUuid) {
//        Unfollow a Product
//        Endpoint: /v1/follow/{user_uuid}/unfollow-product/{product_uuid}
//        Method: DELETE
//        Payload: None (the product to unfollow is specified in the URL)
//        Response: Success confirmation or error message
//        Status: 200 OK (on success), 404 Not Found (user/product not found), etc.
//        Use Case: Allows a user to unfollow a product to stop receiving updates or notifications.
        UUID userUUID;
        UUID productUUID;

        try {
            userUUID = UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid user UUID");
        }

        try {
            productUUID = UUID.fromString(productUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid product UUID");
        }

        try{
            followService.unfollowProduct(userUUID, productUUID);
        } catch (FollowProductException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // TODO unit test
//        UUID productUUID = UUID.fromString(product_uuid);
//        followService.unfollowProduct(uuid);
        return ResponseEntity.ok("Product unfollowed successfully");
    }


}
