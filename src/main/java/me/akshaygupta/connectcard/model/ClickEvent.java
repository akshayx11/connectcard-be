package me.akshaygupta.connectcard.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "click_events")
public class ClickEvent extends BaseEntity {

    @Id
    private String id;

    @Indexed
    private String linkId;

    @Indexed
    private String profileId;

    private String ip;

    private String userAgent;

    private String referrer;

    @Indexed
    private Instant createdAt;
}
