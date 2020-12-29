package com.online.store.services;

import com.online.store.models.User;

import java.util.Optional;

public interface UserService extends Service {

    Iterable<User> listAllUsers();

    Optional<User> getUserById(Integer id);
}
