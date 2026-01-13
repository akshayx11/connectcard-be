package me.akshaygupta.connectcard.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import me.akshaygupta.connectcard.model.Link;

public interface LinkRepository extends MongoRepository<Link, String> {

    List<Link> findByProfileIdOrderByPositionAsc(String profileId);

    List<Link> findByProfileIdAndActiveTrueOrderByPositionAsc(String profileId);

    int countByProfileId(String profileId);

    boolean existsByProfileIdAndPosition(String profileId, int position);

}
