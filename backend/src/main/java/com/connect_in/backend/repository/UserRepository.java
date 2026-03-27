package com.connect_in.backend.repository;

import com.connect_in.backend.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // - save(User user)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - existsById(Long id)

    Optional<User> findUserByEmail(String email); // SELECT * FROM users WHERE email = ?

    boolean existsByEmail(String email); // SELECT COUNT(*) > 0 FROM users WHERE email = ?
}