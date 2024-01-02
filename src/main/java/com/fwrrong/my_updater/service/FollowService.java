package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.model.Follow;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.repository.FollowRepository;
import com.fwrrong.my_updater.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getFollowedProduct(UUID uuid) {
//        uuid: uuid of one AppUser
//        Return: a list of product that this user following
//        TODO
        List<Follow> followList = followRepository.findByUserId(uuid);
        List<Product> productList = new ArrayList<>();
        for(Follow follow:followList){
            productList.add(productRepository.getById(follow.getProductId()));
        }
        return productList;
    }

    public void followProduct(UUID userUUID, UUID productUUID) {
//        TODO: given userId and productId, add a new follow into the table
        Follow follow = new Follow(userUUID, productUUID);
        followRepository.save(follow);
    }

    public void unfollowProduct(UUID uuid) {
//        TODO: given uuid, delete a new follow into the table
        followRepository.deleteById(uuid);

    }
}
