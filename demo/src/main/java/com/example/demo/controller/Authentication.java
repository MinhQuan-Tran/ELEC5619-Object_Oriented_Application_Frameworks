package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

@RestController

public class Authentication {
    @PostMapping("/login")
    public User login(@RequestParam(value = "email", defaultValue = "hello") String name) {
        return new User(10);
    }
}
