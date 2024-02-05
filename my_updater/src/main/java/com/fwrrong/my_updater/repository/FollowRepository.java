package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.Follow;
import com.fwrrong.my_updater.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository
        extends JpaRepository<Follow, UUID> {
    List<Follow> findByUserId(UUID userId);
    void deleteFollowByProductIdAndUserId(@Param("user_id")UUID userId, @Param("product_id")UUID productId);
}
