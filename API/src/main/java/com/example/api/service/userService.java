package com.example.api.service;

import com.example.api.model.Result;
import com.example.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.api.repository.UserRepository;

import java.util.Optional;

@Service
public class userService {
    @Autowired
    private UserRepository userRepository;

/**
 * register
 * @param user parameter encapsulation
 * @return Result
 */
    public Result register(User user) {
        Result<User> result = new Result<>();
        Optional<User> getUser = userRepository.findByEmail(user.getEmail());
        if (getUser.isPresent()) {
            result.setResultFailed("The username already exists!");
            return result;
        }
        user.setPassword(user.getPassword()); // TODO: Consider encrypting the password
        userRepository.save(user);
        result.setResultSuccess("Successfully registered user!", user);
        return result;

    }

    public Result login(String emailOrPhone, String password) {
        Result<String> result = new Result<>();
        User user = userRepository.findByEmail(emailOrPhone)
                .orElse((User) userRepository.findByPhone(emailOrPhone).orElse(null));
        if (user != null && user.getPassword().equals(password)) { // Encrypted passwords should be used in practical applications
            result.setResultSuccess("login successful", user.getToken());

        }else{
            result.setResultFailed("User name and password do not match");
        };
        return result;
    }

    public void changePassword(Integer uid, String token, String newPassword) {
        User user = userRepository.findById(uid).orElse(null);
        if (user != null && user.getToken().equals(token)) {
            user.setPassword(newPassword); // Encrypted passwords should be used in practical applications
            userRepository.save(user);
        }
    }
}
