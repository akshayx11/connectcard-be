package me.akshaygupta.connectcard.service;

import lombok.RequiredArgsConstructor;
import me.akshaygupta.connectcard.dto.UpdateProfileRequest;
import me.akshaygupta.connectcard.exception.ForbiddenException;
import org.springframework.stereotype.Service;

import me.akshaygupta.connectcard.dto.ProfileResponse;
import me.akshaygupta.connectcard.model.Profile;
import me.akshaygupta.connectcard.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileResponse getMyProfile(String userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToResponse(profile);
    }

    public ProfileResponse updateMyProfile(String userId, UpdateProfileRequest req) {

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (req.getDisplayName() != null)
            profile.setDisplayName(req.getDisplayName());

        if (req.getBio() != null)
            profile.setBio(req.getBio());

        if (req.getAvatarUrl() != null)
            profile.setAvatarUrl(req.getAvatarUrl());

        if (req.getTheme() != null)
            profile.setTheme(req.getTheme());

        if (req.getIsPublic() != null)
            profile.setPublic(req.getIsPublic());

        profileRepository.save(profile);

        return getMyProfile(userId);
    }

    public ProfileResponse getPublicProfile(String slug) {

        Profile profile = profileRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (!profile.isPublic()) {
            throw new ForbiddenException("This profile is private");
        }

        profile.setViews(profile.getViews() + 1);
        profileRepository.save(profile);

        return mapToResponse(profile);
    }

    private ProfileResponse mapToResponse(Profile profile) {
        return ProfileResponse.builder()
                .username(profile.getUsername())
                .slug(profile.getSlug())
                .displayName(profile.getDisplayName())
                .bio(profile.getBio())
                .avatarUrl(profile.getAvatarUrl())
                .theme(profile.getTheme())
                .isPublic(profile.isPublic())
                .views(profile.getViews())
                .build();
    }



}
