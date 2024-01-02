package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.AppUser;
import com.fwrrong.my_updater.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID> {

}
