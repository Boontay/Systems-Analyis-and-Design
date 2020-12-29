package com.online.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.Constants;
import com.online.store.exceptions.UnsuccessfulAccountCreationException;
import com.online.store.models.User;
import com.online.store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.regex.Pattern;

/**
 * Contains the endpoint for allowing a user to register their account in the store.
 */
@Controller
public class RegisterAccountController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegisterAccountController.class);

    private UserService userService;

    @Autowired
    public void setProductService(UserService userService) {
        this.userService = userService;
    }

    /**
     * The method to allow a user to register an account.
     * @param email the string relating to the data of the user.
     * @param username the string relating to the data of the user.
     * @param password the string relating to the data of the user.
     * @return A ResponseEntity with the HttpStatus response.
     */

    public boolean validUserData(String email, String username, String password) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        if(email.equals("") || username.equals("") || password.equals("")) {
            LOGGER.error("You Can't Leave any of the Required Fields Empty.");
            return false;
        }
        else if(username.length() < 6 || password.length() < 6) {
            LOGGER.error("Username and Password Length must be 6 Characters or More.");
            return false;
        }
        else if(!emailPattern.matcher(email).matches()) {
            LOGGER.error("Email must be in the Form of 'user@email.com'");
            return false;
        }
        else {
            LOGGER.info("Login was Successful");
            return true;
        }
    }

    public boolean checkIfUserExists(User user) {
        Iterable<User> existingUsers = userService.listAllUsers();

        for (User u : existingUsers) {
            String userEmail = user.getEmail();
            String userUsername = user.getUsername();

            boolean userAccountEmailAlreadyExistsInDatabase = u.getEmail().equals(userEmail);
            boolean userAccountUsernameAlreadyExistsInDatabase = u.getUsername().equals(userUsername);

            if (userAccountEmailAlreadyExistsInDatabase || userAccountUsernameAlreadyExistsInDatabase) {
                return true;
            }
        }

        return false;
    }
    
    public void registerUserAccount(User user) {

        if (!checkIfUserExists(user) && validUserData(user.getEmail(), user.getUsername(), user.getPassword())) {
            userService.save(user);
            
            LOGGER.info("Account has been Created and Registered Correctly.");
        }

        else {
            LOGGER.error("Account Creation was Unsuccessful");
            throw new UnsuccessfulAccountCreationException();
        }
    }

    @PostMapping(Constants.registrationEndpoint)
    public ResponseEntity<String> registerAccount(@RequestBody String string) {
        try {
            User user = new ObjectMapper().readValue(string, User.class);

            registerUserAccount(user);

            return ResponseEntity.ok(string);
        } catch (UnsuccessfulAccountCreationException | JsonProcessingException ex) {
            LOGGER.error("Account Creation was Unsuccessful.");
            return ResponseEntity.badRequest().build();
        }
    }
}
