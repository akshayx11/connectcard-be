package me.akshaygupta.connectcard.dto;

import lombok.Data;

@Data
public class LinkClickCount {
    private String linkId;
    private long clicks;
}
