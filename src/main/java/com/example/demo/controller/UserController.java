package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup") // URL: http://localhost:8080/signup
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET endpoint - test if API is working
    @GetMapping
    @ResponseBody
    public String test() {
        return "Signup API is working!";
    }

    // POST endpoint - handles form submission
    @PostMapping
    @ResponseBody
    public String signup(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String confirmPassword) {

        // 1️⃣ Validate email format - only Gmail addresses allowed
        if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            return "Please enter a valid Gmail address!";
        }

        // 2️⃣ Check if passwords match
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }

        // 3️⃣ Check if email is already registered
        if (userRepository.existsByEmail(email)) {
            return "Email is already registered!";
        }

        // 4️⃣ Create and save user
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);
        userRepository.save(user);

        return "User registered successfully!";
    }
}
