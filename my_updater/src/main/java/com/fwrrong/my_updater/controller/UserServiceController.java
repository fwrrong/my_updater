package com.fwrrong.my_updater.controller;

import com.fwrrong.my_updater.exception.AddUserException;
import com.fwrrong.my_updater.exception.DeleteUserException;
import com.fwrrong.my_updater.exception.GetUserException;
import com.fwrrong.my_updater.exception.ModifyUserException;
import com.fwrrong.my_updater.model.User;
import com.fwrrong.my_updater.service.UserService;
import com.fwrrong.my_updater.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class UserServiceController {

    private UserService userService;
    private ValidationService validationService;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/user/{user_uuid}")
    public ResponseEntity<?> getUser(@PathVariable("user_uuid") String userId){
//        Get one user from user_uuid
//        GET /v1/user/{user_uuid}
//        response: User object (email, UUID, password)
//        status: 200 ok
        UUID uuid;
        User user;

        try {
            uuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        if (!validationService.validateUserId(uuid)) {
            return ResponseEntity.badRequest().body("user id not found");
        }

        try {
            user = userService.getUser(uuid);
        } catch (GetUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // TODO: sensitive information passowrd.
        return ResponseEntity.ok(user);
    }

    @PostMapping("/v1/user")
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> requestBody) {
//        Add one user to MySQL
//        POST /v1/user
//        payload:
//        {
//            "name": "name1",
//            "password": "password1",
//            "email": "email1"
//        }
//        response:
//        User
//        status:
//        202
        String name = requestBody.get("name");
        String password = requestBody.get("password");
        String email = requestBody.get("email");
        User user;

        if (!validationService.validateUserName(name)) {
            return ResponseEntity.badRequest().body("Invalid User Name");
        }

        if (!validationService.validateUserPassword(password)) {
            return ResponseEntity.badRequest().body("Invalid User Password");
        }

        if (!validationService.validateUserEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid User Email");
        }

        try {
            user = userService.addUser(name, password, email);
        } catch (AddUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/v1/user/{user_uuid}")
    public ResponseEntity<?> modifyUser(@RequestBody Map<String, String> requestBody, @PathVariable("user_uuid") String userUuid){
//        Modify one user （email. etc)
//        PUT /v1/user/{user_uuid}
//        payload: User object
//        response: User
//        status: 200
        UUID uuid;
        User modifiedUser;
        String name = requestBody.get("name");
        String password = requestBody.get("password");
        String email = requestBody.get("email");

        try {
            uuid = UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        if (!validationService.validateUserId(uuid)) {
            return ResponseEntity.badRequest().body("user id not found");
        }

        if (!validationService.validateUserName(name)) {
            return ResponseEntity.badRequest().body("Invalid User Name");
        }

        if (!validationService.validateUserPassword(password)) {
            return ResponseEntity.badRequest().body("Invalid User Password");
        }

        if (!validationService.validateUserEmail(email)) {
            return ResponseEntity.badRequest().body("Invalid User Email");
        }

        try {
            modifiedUser = userService.modifyUser(uuid, name, password, email);
        } catch (ModifyUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(modifiedUser);
    }

    @DeleteMapping("/v1/user/{user_uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_uuid") String userUuid){
//        Delete one User
//        DELETE /v1/user/{user_uuid}
//        response: success/failure
//        status: 204
        UUID uuid;

        try {
            uuid = UUID.fromString(userUuid);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID");
        }

        if (!validationService.validateUserId(uuid)) {
            return ResponseEntity.badRequest().body("user id not found");
        }

        try {
            userService.deleteUser(uuid);
        } catch (DeleteUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User deleted successfully");
    }

}
