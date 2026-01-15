package me.akshaygupta.connectcard.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AnalyticsSummaryResponse {
    private long totalClicks;
    private List<LinkAnalyticsDto> links;
}
