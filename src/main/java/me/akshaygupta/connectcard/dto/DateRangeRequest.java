package me.akshaygupta.connectcard.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class DateRangeRequest {
    private Instant from;
    private Instant to;
}
