package me.akshaygupta.connectcard.repository;

import me.akshaygupta.connectcard.dto.LinkClickCount;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import me.akshaygupta.connectcard.model.ClickEvent;

import java.time.Instant;
import java.util.List;

public interface ClickEventRepository extends MongoRepository<ClickEvent, String> {

    long countByLinkId(String linkId);

    long countByProfileId(String profileId);

    @Aggregation(pipeline = {
            "{ $match: { profileId: ?0 } }",
            "{ $group: { _id: '$linkId', clicks: { $sum: 1 } } }",
            "{ $project: { linkId: '$_id', clicks: 1, _id: 0 } }"
    })
    List<LinkClickCount> aggregateClicksByProfile(String profileId);

    @Aggregation(pipeline = {
            "{ $match: { profileId: ?0, created_at: { $gte: ?1, $lte: ?2 } } }",
            "{ $group: { _id: '$linkId', clicks: { $sum: 1 } } }",
            "{ $project: { linkId: '$_id', clicks: 1, _id: 0 } }"
    })
    List<LinkClickCount> aggregateClicksByProfileAndDate(
            String profileId,
            Instant from,
            Instant to
    );

}
