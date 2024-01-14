package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.Follow;
import com.fwrrong.my_updater.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository
        extends JpaRepository<Follow, UUID> {
//    @Query(value = "SELECT product_id FROM follow WHERE user_id = ?",
//    nativeQuery = true)
    List<Follow> findByUserId(UUID userId);
    void deleteFollowByProductIdAndUserId(UUID userUUID, UUID productUUID);
//    Optional<Follow> findById(UUID uuid);
}
