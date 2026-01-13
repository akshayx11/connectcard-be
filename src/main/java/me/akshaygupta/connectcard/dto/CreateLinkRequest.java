package me.akshaygupta.connectcard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateLinkRequest {

    @NotBlank
    private String title;

    @NotBlank
    @Pattern(regexp = "https?://.*", message = "URL must start with http or https")
    private String url;

    private String icon;
}
