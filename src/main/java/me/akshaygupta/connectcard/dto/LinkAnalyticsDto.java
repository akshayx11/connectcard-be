package me.akshaygupta.connectcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkAnalyticsDto {
    private String linkId;
    private String title;
    private long clicks;
}
