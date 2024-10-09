package io.b3.Services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Hashes the password using BCrypt
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Checks if the raw password matches the hashed password
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
