package me.akshaygupta.connectcard.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "mongo_test")
public class MongoTest extends BaseEntity {

    @Id
    private String id;
    private String name;
}
