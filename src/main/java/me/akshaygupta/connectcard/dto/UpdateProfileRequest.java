package me.akshaygupta.connectcard.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import me.akshaygupta.connectcard.enums.ProfileTheme;

@Data
public class UpdateProfileRequest {

    @Size(min = 2, max = 50)
    private String displayName;

    @Size(max = 160)
    private String bio;

    private String avatarUrl;

    private ProfileTheme theme;

    private Boolean isPublic;
}
