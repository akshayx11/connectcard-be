package me.akshaygupta.connectcard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import me.akshaygupta.connectcard.dto.ApiResponse;
import me.akshaygupta.connectcard.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMyProfile() {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile fetched",
                        profileService.getMyProfile(userId)
                )
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<?>> updateMyProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile updated",
                        profileService.updateMyProfile(userId, request)
                )
        );
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<?>> getPublicProfile(@PathVariable String slug) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile fetched",
                        profileService.getPublicProfile(slug)
                )
        );
    }


}
