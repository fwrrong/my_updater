package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository
        extends MongoRepository<Product, UUID> {

//    @Query("{ '_id' : ?0 }")
//    Optional<Product> findByUuid(UUID id);
//
//    @Query("{ '_id' : ?0 }")
//    Optional<Product> findByStrId(String id);
//    Optional<Product> findById(UUID uuid);
    Optional<Product> findByName(String name);

}
