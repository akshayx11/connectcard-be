package me.akshaygupta.connectcard.service;

import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.RegisterRequest;
import me.akshaygupta.connectcard.exception.DuplicateResourceException;
import me.akshaygupta.connectcard.model.Profile;
import me.akshaygupta.connectcard.model.User;
import me.akshaygupta.connectcard.repository.ProfileRepository;
import me.akshaygupta.connectcard.repository.UserRepository;
import me.akshaygupta.connectcard.util.SlugUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
            throw new DuplicateResourceException("Email already in use");
        }

        if (userRepository.existsByUsername(request.getUsername().toLowerCase())) {
            throw new DuplicateResourceException("Username already taken");
        }


        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = userRepository.save(
                User.builder()
                        .email(request.getEmail())
                        .username(request.getUsername())
                        .password(hashedPassword)
                        .build()
        );
        // 🔗 Create Profile automatically
        String baseSlug = SlugUtil.toSlug(request.getUsername());
        // Slug is not username — slug is permanent.
        String slug = baseSlug;
        int i = 1;

        while (profileRepository.existsByUsername(slug)) {
            slug = baseSlug + i++;
        }

        profileRepository.save(
                Profile.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .slug(slug)
                        .displayName(user.getUsername())
                        .build()
        );

        return user;
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