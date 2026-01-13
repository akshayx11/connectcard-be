package me.akshaygupta.connectcard.service;

import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.RegisterRequest;
import me.akshaygupta.connectcard.exception.DuplicateResourceException;
import me.akshaygupta.connectcard.model.User;
import me.akshaygupta.connectcard.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
            throw new DuplicateResourceException("Email already in use");
        }

        if (userRepository.existsByUsername(request.getUsername().toLowerCase())) {
            throw new DuplicateResourceException("Username already taken");
        }


        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(hashedPassword)
                .build();

        return userRepository.save(user);
    }

    public User login(String usernameOrEmail, String password) {

        User user = userRepository.findByEmail(usernameOrEmail.toLowerCase())
                .or(() -> userRepository.findByUsername(usernameOrEmail.toLowerCase()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        user.setLastLogin(Instant.now());
        userRepository.save(user);

        return user;
    }

//Access User in Controllers
//Anywhere in your API:
//    String userId = SecurityContextHolder.getContext()
//            .getAuthentication()
//            .getPrincipal()
//            .toString();

}