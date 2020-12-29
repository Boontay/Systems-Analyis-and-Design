package com.online.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.online.store.Constants;
import com.online.store.exceptions.UnsuccessfulLoginException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.models.User;
import com.online.store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contains the endpoint for logging a user in.
 */
@Controller
public class LogInController {
    private final Logger LOGGER = LoggerFactory.getLogger(LogInController.class);

    private UserService userService;

    @Autowired
    public void setProductService(UserService userService) {
        this.userService = userService;
    }

    /**
     * The method allows a user to login to their account.
     * @param user the data relating to the user logging in.
     * @return A boolean to indicate if a login attempt is valid.
     */

    public boolean checkIfValidLogin(User user) {
        Iterable<User> existingUsers = userService.listAllUsers();

        for (User u : existingUsers) {
            String userPassword = user.getPassword();
            String userUsername = user.getUsername();

            boolean userAccountUsernameExistsInDatabase = u.getUsername().equals(userUsername);
            boolean userAccountPasswordExistsInDatabase = u.getPassword().equals(userPassword);

            if (userAccountPasswordExistsInDatabase && userAccountUsernameExistsInDatabase) {
                return true;
            }
        }

        return false;
    }

    public void validLogin(User user) {

        if (checkIfValidLogin(user)) {
            LOGGER.info("Login has been Successful.");
        }

        else {
            LOGGER.error("No matching Login Details have been found in the Database.");
            throw new UnsuccessfulLoginException();
        }
    }

    @PostMapping(Constants.loginEndpoint)
    public ResponseEntity<String> logIn(@RequestBody String string) {
        try {
            User user = new ObjectMapper().readValue(string, User.class);

            validLogin(user);

            return ResponseEntity.ok(string);
        } catch (UnsuccessfulLoginException | JsonProcessingException ex) {
            LOGGER.error("Account registration unsuccessful.");
            return ResponseEntity.badRequest().build();
        }
    }
}
