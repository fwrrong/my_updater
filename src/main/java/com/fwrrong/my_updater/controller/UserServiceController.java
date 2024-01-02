package com.fwrrong.my_updater.controller;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.model.AppUser;
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
    public ResponseEntity<AppUser> getUser(@PathVariable String user_uuid){
//        Get one user from user_uuid
//        GET /v1/user/{user_uuid}
//        response: User object (email, UUID, password)
//        status: 200 ok
        UUID uuid = UUID.fromString(user_uuid);
        AppUser user = userService.getUser(uuid);
        // TODO: sensitive information passowrd.
        return ResponseEntity.ok(user);
    }

    @PostMapping("/v1/user")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
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
    public ResponseEntity<AppUser> modifyUser(@RequestBody AppUser user, @PathVariable String user_uuid){
//        Modify one user （email. etc)
//        PUT /v1/user/{user_uuid}
//        payload: User object
//        response: User
//        status: 200
        UUID uuid = UUID.fromString(user_uuid);
        AppUser modifiedUser = userService.modifyUser(uuid, user);
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

}
