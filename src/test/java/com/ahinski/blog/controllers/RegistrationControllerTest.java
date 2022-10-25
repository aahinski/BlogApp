package com.ahinski.blog.controllers;

import com.ahinski.blog.models.User;
import com.ahinski.blog.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Map<String, Object> model;

    @Test
    void alreadyExistsAddUserTest() {
        User newUser = new User("haaland", "5touches5goals");
        User existingUser = new User("haaland", "toworkhard");

        when(userRepository.findByUsername(newUser.getUsername()))
                .thenReturn(existingUser);

        assertEquals("registration", registrationController.addUser(newUser, model));
    }

    @Test
    void successfulAddUserTest() {
        User newUser = new User("barney", "legenwaitforitdary");

        when(userRepository.findByUsername(newUser.getUsername()))
                .thenReturn(null);

        assertEquals("redirect:/login", registrationController.addUser(newUser, model));
    }
}