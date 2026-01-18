package me.akshaygupta.connectcard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.ApiResponse;
import me.akshaygupta.connectcard.dto.LoginRequest;
import me.akshaygupta.connectcard.dto.RegisterRequest;
import me.akshaygupta.connectcard.model.User;
import me.akshaygupta.connectcard.service.JwtService;
import me.akshaygupta.connectcard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request) {

        User user = userService.login(
                request.getUsernameOrEmail(),
                request.getPassword()
        );

        String token = jwtService.generateToken(user.getId(), user.getRole());

        return ResponseEntity.ok(
                ApiResponse.success("Login successful", token)
        );
    }
}
