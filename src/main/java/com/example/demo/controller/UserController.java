package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup") // URL: http://localhost:8080/signup
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // ✅ GET endpoint - test if API is working
    @GetMapping
    @ResponseBody
    public String test() {
        return "Signup API is working!";
    }

    // ✅ POST endpoint - handles signup and sends verification code
    @PostMapping
    public String signup(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String confirmPassword) {

        // 1️⃣ Validate email format - only Gmail addresses allowed
        if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            return "redirect:/signup.html?error=invalid_email";
        }

        // 2️⃣ Check if passwords match
        if (!password.equals(confirmPassword)) {
            return "redirect:/signup.html?error=password_mismatch";
        }

        // 3️⃣ Check if email is already registered
        if (userRepository.existsByEmail(email)) {
            return "redirect:/signup.html?error=email_exists";
        }

        // 4️⃣ Create user and generate verification code
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);

        String code = emailService.generateVerificationCode();
        user.setVerificationCode(code);
        user.setVerified(false);

        // 5️⃣ Save user to database
        userRepository.save(user);

        // 6️⃣ Send verification email
        try {
            emailService.sendVerificationEmail(email, code);
            System.out.println("Verification code sent: " + code + " to " + email);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup.html?error=email_failed";
        }

        // 7️⃣ Redirect to verification page after successful signup
        return "redirect:/EmailVerification.html";
    }

    // ✅ Verify endpoint - checks code
    @PostMapping("/verify")
    @ResponseBody
    public String verifyUser(@RequestParam String email, @RequestParam String code) {
        User user = userRepository.findByEmail(email);

        if (user != null && code.equals(user.getVerificationCode())) {
            user.setVerified(true);
            user.setVerificationCode(null); // clear after success
            userRepository.save(user);
            return "User verified successfully!";
        } else {
            return "Invalid verification code!";
        }
    }
}
