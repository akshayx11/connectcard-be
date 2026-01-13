package me.akshaygupta.connectcard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClickRequest {
    @NotBlank
    private String linkId;
}
