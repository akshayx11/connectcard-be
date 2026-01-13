package me.akshaygupta.connectcard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDbFactory,
            MongoMappingContext mappingContext,
            MongoCustomConversions conversions) {

        var dbRefResolver = new org.springframework.data.mongodb.core.convert.DefaultDbRefResolver(mongoDbFactory);

        var converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(conversions);

        // Removes the _class field
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }
}
