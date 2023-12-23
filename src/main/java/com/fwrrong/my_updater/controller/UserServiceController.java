package com.fwrrong.my_updater.controller;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.model.User;
import com.fwrrong.my_updater.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserServiceController {

    private UserService userService;
    @GetMapping("/v1/user/{user_uuid}")
    public ResponseEntity<User> getUser(@PathVariable String user_uuid){
//        Get one user from user_uuid
//        GET /v1/user/{user_uuid}
//        response: User object (email, UUID, password)
//        status: 200 ok
        UUID uuid = UUID.fromString(user_uuid);
        User user = userService.getUser(uuid);
        // TODO: sensitive information passowrd.
        return ResponseEntity.ok(user);
    }

    @PostMapping("/v1/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
//        Add one user to MySQL
//        POST /v1/user
//        payload:User object (...)
//        response:
//        User
//        status:
//        202
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/v1/user/{user_uuid}")
    public ResponseEntity<User> modifyUser(@RequestBody User user, @PathVariable String user_uuid){
//        Modify one user （email. etc)
//        PUT /v1/user/{user_uuid}
//        payload: User object
//        response: User
//        status: 200
        UUID uuid = UUID.fromString(user_uuid);
        User modifiedUser = userService.modifyUser(uuid, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/v1/user/{user_uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable String user_uuid){
//        Delete one User
//        DELETE /v1/user/{user_uuid}
//        response: success/failure
//        status: 204
        UUID uuid = UUID.fromString(user_uuid);
        userService.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/user/{user_uuid}/followed-products")
    public ResponseEntity<List<Product>> getFollowedProduct(@PathVariable String user_uuid){
//        Get User's Followed Products
//        GET /v1/user/{user_uuid}/followed-products
//        Response: List of Product objects
//        Status: 200 OK
//        Use Case: Allows a user to see all the products they are following.
        UUID uuid = UUID.fromString(user_uuid);
        List<Product> productList = userService.getFollowedProduct(uuid);
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/v1/user/{user_uuid}/follow-product")
    public ResponseEntity<?> followProduct(@PathVariable String user_uuid, @RequestBody Map<String, String> requestBody) {
//    Follow a Product
//    Endpoint: /v1/user/{user_uuid}/follow-product
//    Method: POST
//    Payload: { "product_uuid": "product-uuid-here" }
//    Response: Success confirmation or error message
//    Status: 200 OK (on success), 404 Not Found (user/product not found), etc.
//    Use Case: Allows a user to follow a product to receive updates or notification.
        UUID userUUID = UUID.fromString(user_uuid);
        UUID productUUID = UUID.fromString(requestBody.get("product_uuid"));
        userService.followProduct(userUUID, productUUID);
        return ResponseEntity.ok("Product followed successfully");
    }

    @DeleteMapping("/v1/user/{user_uuid}/followed-products/{product_uuid}")
    public ResponseEntity<?> followProduct(@PathVariable String user_uuid, @PathVariable String product_uuid) {
//        Unfollow a Product
//        Endpoint: /v1/user/{user_uuid}/followed-products/{product_uuid}
//        Method: DELETE
//        Payload: None (the product to unfollow is specified in the URL)
//        Response: Success confirmation or error message
//        Status: 200 OK (on success), 404 Not Found (user/product not found), etc.
//        Use Case: Allows a user to unfollow a product to stop receiving updates or notifications.

        UUID userUUID = UUID.fromString(user_uuid);
        UUID productUUID = UUID.fromString(product_uuid);
        userService.unfollowProduct(userUUID, productUUID);
        return ResponseEntity.ok("Product unfollowed successfully");
    }


}
