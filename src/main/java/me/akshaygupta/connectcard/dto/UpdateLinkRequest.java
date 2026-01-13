package me.akshaygupta.connectcard.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateLinkRequest {

    private String title;

    @Pattern(regexp = "https?://.*", message = "URL must start with http or https")
    private String url;

    private String icon;

    private Boolean active;

    private Integer position;
}
