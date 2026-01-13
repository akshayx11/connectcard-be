package me.akshaygupta.connectcard.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import me.akshaygupta.connectcard.model.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByUserId(String userId);

    boolean existsByUsername(String username);

    Optional<Profile> findBySlug(String slug);
}
