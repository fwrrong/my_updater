package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.exception.FollowProductException;
import com.fwrrong.my_updater.exception.GetFollowedProductException;
import com.fwrrong.my_updater.exception.UnfollowProductException;
import com.fwrrong.my_updater.model.Follow;
import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.repository.FollowRepository;
import com.fwrrong.my_updater.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FollowService {
    private FollowRepository followRepository;

    private ProductRepository productRepository;

    public FollowService(FollowRepository followRepository, ProductRepository productRepository) {
        this.followRepository = followRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getFollowedProduct(UUID userUUID) {
//        uuid: uuid of one User
//        Return: a list of product that this user following
//        TODO: unit test
        List<Follow> followList;
        List<Product> productList = new ArrayList<>();

        try{
            followList = followRepository.findByUserId(userUUID);
        } catch (IllegalArgumentException e){
            throw new GetFollowedProductException("Invalid user ID", e);
        }
        List<Product> all = productRepository.findAll();
        for(Follow follow:followList){
            try{
                UUID productId = follow.getProductId();
                Optional<Product> product = productRepository.findById(productId);
                productList.add(product.orElse(null));
            } catch (IllegalArgumentException e) {
                throw new GetFollowedProductException("Invalid product ID", e);
            }
        }
        return productList;
    }

    public void followProduct(UUID userUUID, UUID productUUID) {
//        given userId and productId, add a new follow into the table
        // TODO: unit test
        Follow follow = new Follow(userUUID, productUUID);
        try{
            followRepository.save(follow);
        } catch (IllegalArgumentException e) {
            throw new FollowProductException("Invalid follow", e);
        } catch(OptimisticLockingFailureException e) {
            throw new FollowProductException("Internal Error, please try again", e);
        }
    }

    public void unfollowProduct(UUID userUUID, UUID productUUID) {
//       given uuid, delete a new follow into the table
        // TODO: unit test
        try{
            followRepository.deleteFollowByProductIdAndUserId(userUUID, productUUID);
        } catch (IllegalArgumentException e) {
            throw new UnfollowProductException("Invalid unfollow", e);
        }
    }
}
