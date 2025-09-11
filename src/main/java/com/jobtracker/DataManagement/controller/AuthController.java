package com.jobtracker.DataManagement.controller;


import com.jobtracker.DataManagement.model.User;
import com.jobtracker.DataManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jobtracker.DataManagement.security.JwtUtil;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; // Autowire JwtUtil to generate the token here

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> payload) {
        return userService.register(payload.get("name"), payload.get("email"), payload.get("password"));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> payload) {
        User user = userService.login(payload.get("email"), payload.get("password"));
        String token = jwtUtil.generateToken(user.getId()); // Generate the token using the user's ID
        //String token = userService.login(payload.get("email"), payload.get("password"));

        return Map.of("token", token, "userName", user.getName());
    }
}