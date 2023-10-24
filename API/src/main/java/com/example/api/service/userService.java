package com.example.api.service;

import com.example.api.dto.UserDTO;
import com.example.api.model.Result;
import com.example.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.api.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
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
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    /**
     * register
     * 
     * @param user parameter encapsulation
     * @return Result
     */
    public Result register(User user) {
        Result result = new Result();
        Optional<User> getUser = userRepository.findByEmail(user.getEmail());
        if (getUser.isPresent()) {
            result.setResultFailed(2);
            return result;
        }
        // encode password by Bcrypt
        String plainPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        UserDTO userDTO = convertToDto(user);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("user",userDTO);
        result.setResultSuccess(0, resultMap);
        return result;

    }

    public Result login(String emailOrPhone, String plainPassword) {

        Result result = new Result();
        // Encode login password
        String hashedPassword = passwordEncoder.encode(plainPassword);
        // validate password
        User user = userRepository.findByEmail(emailOrPhone)
                .orElse((User) userRepository.findByPhone(emailOrPhone).orElse(null));
        if (user != null && passwordEncoder.matches(plainPassword, user.getPassword())) { // Encrypted passwords should
                                                                                          // be used in practical
                                                                                          // applications
            UserDTO userDTO = convertToDto(user);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user",userDTO);
            result.setResultSuccess(0, resultMap);

        } else {
            result.setResultFailed(1);
        }

        return result;
    }

    public void changePassword(Integer uid, String token, String newPassword) {
        User user = userRepository.findById(uid).orElse(null);
        if (user != null && user.getToken().equals(token)) {

            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(newPassword); // Encrypted passwords should be used in practical applications
            userRepository.save(user);
        }
    }

    public void findUserById(Integer uid) {
        Result result = new Result();
        User user = userRepository.findById(uid).orElse(null);
        if (user != null) {

            UserDTO userDTO = convertToDto(user);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("user",userDTO);

            result.setResultSuccess(0, resultMap);
        }else{
            result.setResultFailed(3);
        }
    }
}
