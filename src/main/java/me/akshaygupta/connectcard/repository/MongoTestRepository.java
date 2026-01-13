package me.akshaygupta.connectcard.repository;

import me.akshaygupta.connectcard.model.MongoTest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoTestRepository extends MongoRepository<MongoTest, String> {
}
