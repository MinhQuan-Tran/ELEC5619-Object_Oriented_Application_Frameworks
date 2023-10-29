package com.example.api.service;

import com.example.api.dto.UserDTO;
import com.example.api.model.Result;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        Result result = userService.register(user);

        // Assert
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testRegisterUserEmailAlreadyExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act
        Result result = userService.register(user);

        // Assert
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        Result result = userService.login("test@example.com", "password");

        // Assert
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testLoginFailure() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        Result result = userService.login("wrong@example.com", "password");

        // Assert
        assertNotNull(result, "Result should not be null");
    }

    @Test
    void testFindUserByIdUserExists() {
        // Arrange
        User user = new User();
        user.setUid(1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Act
        UserDTO userDTO = userService.findUserById(1);

        // Assert
        assertNotNull(userDTO, "UserDTO should not be null");
    }

    @Test
    void testFindUserByIdUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        UserDTO userDTO = userService.findUserById(1);

        // Assert
        assertNull(userDTO, "UserDTO should be null");
    }

    @Test
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setUid(1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User updatedUser = userService.updateUser(1, user);

        // Assert
        assertNotNull(updatedUser, "Updated user should not be null");
    }

    // Additional test cases can be added as needed.
}
