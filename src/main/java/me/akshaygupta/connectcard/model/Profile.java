package me.akshaygupta.connectcard.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import me.akshaygupta.connectcard.enums.ProfileTheme;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile extends BaseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String slug;

    private String displayName;

    private String bio;

    private String avatarUrl;

    @Builder.Default
    private ProfileTheme theme = ProfileTheme.LIGHT;

    @Builder.Default
    private boolean isPublic = true;

    @Builder.Default
    private long views = 0;
}
