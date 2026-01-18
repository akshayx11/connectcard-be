package me.akshaygupta.connectcard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.akshaygupta.connectcard.enums.AccountStatus;
import me.akshaygupta.connectcard.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String username;

    private String password;

    @Builder.Default
    private List<String> roles = List.of("USER");

    @Builder.Default
    private boolean emailVerified = false;

    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;

    private Instant lastLogin;

    @Builder.Default
    private Role role = Role.USER;

    // Normalize data before saving
    public void setEmail(String email) {
        this.email = email == null ? null : email.toLowerCase();
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.toLowerCase();
    }
}