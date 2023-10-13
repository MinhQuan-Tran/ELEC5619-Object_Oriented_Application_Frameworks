package com.example.api.controller;

import com.example.api.dto.*;
import com.example.api.model.*;
import com.example.api.repository.*;
import com.example.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private userService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * *
     * 
     * @param user
     * @return userDTO
     */
    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(user.getUid());
        userDTO.setNickname(user.getNickname());
        userDTO.setName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    @PostMapping(value = "/register", consumes = { "multipart/form-data" })
    public ResponseEntity<Result> register(User user) {
        Result result = userService.register(user);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestParam String emailOrPhone, @RequestParam String password) {
        // Result result = userService.login(emailOrPhone, password);
        // return new ResponseEntity<>(result, HttpStatus.OK);

        Result<UserDTO> result = new Result<UserDTO>();

        String hashedPassword = passwordEncoder.encode(password);

        User user = userRepository.findByEmail(emailOrPhone)
                .orElse((User) userRepository.findByPhone(emailOrPhone).orElse(null));

        if (user != null && passwordEncoder.matches(password, user.getPassword())) { // Encrypted passwords should
                                                                                     // be used in practical
                                                                                     // applications
            UserDTO userDTO = convertToDto(user);
            result.setResultSuccess("Login success", userDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.setResultFailed("Login failed");
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam Integer uid, @RequestParam String token,
            @RequestParam String newPassword) {
        userService.changePassword(uid, token, newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }

}