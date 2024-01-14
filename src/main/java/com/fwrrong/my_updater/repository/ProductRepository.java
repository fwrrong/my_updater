package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository
        extends MongoRepository<Product, UUID> {

}
