package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.AppUser;
import com.fwrrong.my_updater.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRepository
        extends JpaRepository<Follow, UUID> {
    List<Follow> findByUserId(UUID userId);
}
