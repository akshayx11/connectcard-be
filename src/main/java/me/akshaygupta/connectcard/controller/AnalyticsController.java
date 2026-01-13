package me.akshaygupta.connectcard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import me.akshaygupta.connectcard.dto.ApiResponse;
import me.akshaygupta.connectcard.dto.ClickRequest;
import me.akshaygupta.connectcard.service.ClickService;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final ClickService clickService;

    @PostMapping("/click")
    public ResponseEntity<ApiResponse<?>> trackClick(
            @Valid @RequestBody ClickRequest request,
            HttpServletRequest httpRequest) {

        clickService.registerClick(request.getLinkId(), httpRequest);

        return ResponseEntity.ok(
                ApiResponse.success("Click recorded", null)
        );
    }
}
