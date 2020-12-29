package com.online.store.services;

import com.online.store.models.User;
import com.online.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setProductRepository(UserRepository productRepository) {
        this.userRepository = productRepository;
    }

    /**
     * This gets all the users stored in the database.
     * @return The Iterable list of all the users in the database.
     */
    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This gets a user by a specified id.
     * @param id The id of the desired product.
     * @return Returns the product specified by the id.
     */
    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Object save(Object object) {
        return userRepository.save((User)object);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
