package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Check if email already exists
    boolean existsByEmail(String email);

    // ✅ Find user by email
    User findByEmail(String email);
}
