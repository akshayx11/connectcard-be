package me.akshaygupta.connectcard.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token_blacklist")
public class BlacklistedToken {

    @Id
    private String token;

    private Instant expiresAt;

    public BlacklistedToken(String token, Instant expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
