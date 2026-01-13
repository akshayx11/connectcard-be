package me.akshaygupta.connectcard.dto;

import lombok.Builder;
import lombok.Data;
import me.akshaygupta.connectcard.enums.ProfileTheme;

@Data
@Builder
public class ProfileResponse {

    private String username;
    private String slug;
    private String displayName;
    private String bio;
    private String avatarUrl;
    private ProfileTheme theme;
    private boolean isPublic;
    private long views;
}
