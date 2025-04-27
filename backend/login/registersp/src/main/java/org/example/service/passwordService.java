package org.example.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class passwordService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Hash the password before saving
    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // Verify a password during login
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
