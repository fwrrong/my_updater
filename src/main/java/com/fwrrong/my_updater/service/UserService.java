package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {
    public User getUser(UUID uuid) {
//        TODO: gerUser
        User user = new User();
        return user;
    }

    public void addUser(User user) {
//        TODO
    }

    public User modifyUser(UUID uuid, User user) {
//        TODO: gerUser
        return user;
    }

    public void deleteUser(UUID uuid) {
//        TODO
    }

    public List<Product> getFollowedProduct(UUID uuid) {
//        TODO
        List<Product> productList = new ArrayList<>();
        return productList;
    }

    public void followProduct(UUID userUUID, UUID productUUID) {
//        TODO
    }

    public void unfollowProduct(UUID userUUID, UUID productUUID) {
//        TODO
    }
}
