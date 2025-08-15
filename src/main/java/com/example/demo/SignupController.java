package com.example.demo; // make sure it's inside your main package!

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RestController
@RequestMapping("/api/signup")
@CrossOrigin(origins = "http://localhost:5500") // change port to where your frontend runs
public class SignupController {

    @PostMapping
    public ResponseEntity<String> signup(@RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/userdata", "root", "password")) {

            String query = "INSERT INTO users (email, password, confirm_password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, confirmPassword);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while signing up");
        }

        return ResponseEntity.ok("Signup successful");
    }
}
