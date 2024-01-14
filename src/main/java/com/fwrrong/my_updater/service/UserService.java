package com.fwrrong.my_updater.service;

import com.fwrrong.my_updater.exception.*;
import com.fwrrong.my_updater.model.User;
import com.fwrrong.my_updater.repository.UserRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(UUID uuid) {
//        TODO: unit test
        Optional<User> user;

        try {
            user = userRepository.findById(uuid);
        } catch (IllegalArgumentException e) {
            throw new GetUserException("Invalid ID", e);
        }

        return user.orElse(null);
    }

    public User addUser(String name, String password, String email) {
//        TODO: unit test
        User user = new User(name, password, email);

        try {
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new AddUserException("Invalid user", e);
        } catch (OptimisticLockingFailureException e) {
            throw new AddUserException("Internal Error, please try again", e);
        }

        return user;
    }

    public User modifyUser(UUID uuid, String name, String password, String email) {
//        TODO: unit test
        User user = new User(uuid, name, password, email);
        User updatedUser;

        try {
            updatedUser = userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new ModifyUserException("Invalid user", e);
        } catch (OptimisticLockingFailureException e) {
            throw new ModifyUserException("Internal Error, please try again", e);
        }

        return updatedUser;

    }

    public void deleteUser(UUID uuid) {
//        TODO: unit test
        try {
            userRepository.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new DeleteUserException("Invalid ID", e);
        }

    }
}
