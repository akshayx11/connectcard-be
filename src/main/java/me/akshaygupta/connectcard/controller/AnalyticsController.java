package me.akshaygupta.connectcard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.repository.ProfileRepository;
import me.akshaygupta.connectcard.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import me.akshaygupta.connectcard.dto.ApiResponse;
import me.akshaygupta.connectcard.dto.ClickRequest;
import me.akshaygupta.connectcard.service.ClickService;

import java.time.Instant;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ClickService clickService;
    private final ProfileRepository profileRepository;
    private final AnalyticsService analyticsService;

    @PostMapping("/click")
    public ResponseEntity<ApiResponse<?>> trackClick(
            @Valid @RequestBody ClickRequest request,
            HttpServletRequest httpRequest) {

        clickService.registerClick(request.getLinkId(), httpRequest);

        return ResponseEntity.ok(
                ApiResponse.success("Click recorded", null)
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<?>> getSummary(
            @RequestParam(required = false) Instant from,
            @RequestParam(required = false) Instant to) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        String profileId = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"))
                .getId();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Analytics summary",
                        analyticsService.getSummary(profileId, from, to)
                )
        );
    }
}
