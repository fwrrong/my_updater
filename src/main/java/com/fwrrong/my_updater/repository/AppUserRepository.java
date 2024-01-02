package com.fwrrong.my_updater.repository;

import com.fwrrong.my_updater.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepository
        extends JpaRepository<AppUser, UUID> {

}
