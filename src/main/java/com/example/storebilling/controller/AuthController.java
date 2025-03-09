package com.example.storebilling.controller;

import com.example.storebilling.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String login(@RequestParam String username) {
        return jwtUtil.generateToken(username);
    }
}
