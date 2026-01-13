package me.akshaygupta.connectcard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import me.akshaygupta.connectcard.model.BlacklistedToken;

public interface BlacklistedTokenRepository
        extends MongoRepository<BlacklistedToken, String> {
}
