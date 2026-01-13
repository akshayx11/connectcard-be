package me.akshaygupta.connectcard.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class ReorderLinksRequest {
    @NotEmpty
    private List<String> linkIds;
}
