package com.jose.ecommerce.controllers;

import com.jose.ecommerce.model.persistence.User;
import com.jose.ecommerce.model.persistence.repositories.CartRepository;
import com.jose.ecommerce.model.persistence.repositories.UserRepository;
import com.jose.ecommerce.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    private UserController userController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void init() {
        userController = new UserController(userRepository, cartRepository, bCryptPasswordEncoder);
    }

    @Test
    public void createUserSuccess() {

        CreateUserRequest createUserRequest = new CreateUserRequest("josesoto", "123456", "123456");

        when(bCryptPasswordEncoder.encode("123456")).thenReturn("123456_hashed");

        ResponseEntity<User> userResponseEntity = userController.createUser(createUserRequest);
        assertNotNull(userResponseEntity);
        assertEquals(200, userResponseEntity.getStatusCodeValue());

        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("josesoto", user.getUsername());
        assertEquals("123456_hashed", user.getPassword());

    }

    @Test
    public void createUserError() {

        CreateUserRequest createUserRequest = new CreateUserRequest("josesoto", "123", "123");

        // Will fail as password length < 6 characters
        ResponseEntity<User> response = userController.createUser(createUserRequest);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

    }

    @Test
    public void findUserById() {

        long userId = 1L;
        String username = "josesoto";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User createdUser = userController.findById(userId).getBody();

        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());

    }

    @Test
    public void findUserbyUsername() {

        long userId = 1L;
        String username = "josesoto";
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User createdUser = userController.findByUserName(username).getBody();

        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());

    }

}
