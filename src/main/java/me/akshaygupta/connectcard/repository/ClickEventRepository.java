package me.akshaygupta.connectcard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import me.akshaygupta.connectcard.model.ClickEvent;

public interface ClickEventRepository extends MongoRepository<ClickEvent, String> {

    long countByLinkId(String linkId);

    long countByProfileId(String profileId);
}
