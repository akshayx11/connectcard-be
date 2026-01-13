package me.akshaygupta.connectcard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.ReorderLinksRequest;
import me.akshaygupta.connectcard.dto.UpdateLinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import me.akshaygupta.connectcard.dto.ApiResponse;
import me.akshaygupta.connectcard.dto.CreateLinkRequest;
import me.akshaygupta.connectcard.model.Link;
import me.akshaygupta.connectcard.repository.ProfileRepository;
import me.akshaygupta.connectcard.service.LinkService;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;
    private final ProfileRepository profileRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createLink(
            @Valid @RequestBody CreateLinkRequest request) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        String profileId = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"))
                .getId();

        Link link = linkService.createLink(profileId, request);

        return ResponseEntity.ok(
                ApiResponse.success("Link created", link)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getMyLinks() {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        String profileId = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"))
                .getId();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Links fetched",
                        linkService.getMyLinks(profileId)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateLink(
            @PathVariable String id,
            @Valid @RequestBody UpdateLinkRequest request) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        String profileId = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"))
                .getId();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Link updated",
                        linkService.updateLink(profileId, id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLink(@PathVariable String id) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        String profileId = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"))
                .getId();

        linkService.deleteLink(profileId, id);

        return ResponseEntity.ok(
                ApiResponse.success("Link deleted", null)
        );
    }

    @PutMapping("/reorder")
    public ResponseEntity<ApiResponse<?>> reorderLinks(
            @Valid @RequestBody ReorderLinksRequest request) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();

        String profileId = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"))
                .getId();

        linkService.reorderLinks(profileId, request.getLinkIds());

        return ResponseEntity.ok(
                ApiResponse.success("Links reordered", null)
        );
    }

}
