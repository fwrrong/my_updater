package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.Product;
import com.fwrrong.my_updater.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository
        extends JpaRepository<User, UUID> {
//    Optional<User> findById(UUID uuid);

}
