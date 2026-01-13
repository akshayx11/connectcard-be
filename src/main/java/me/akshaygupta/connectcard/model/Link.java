package me.akshaygupta.connectcard.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "links")
@CompoundIndex(name = "profile_position_idx", def = "{'profileId':1,'position':1}")
public class Link extends BaseEntity {

    @Id
    private String id;

    private String profileId;

    private String title;

    private String url;

    private String icon;

    private int position;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private long clicks = 0;
}
