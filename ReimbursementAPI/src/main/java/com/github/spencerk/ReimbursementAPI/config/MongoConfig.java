package com.github.spencerk.ReimbursementAPI.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.github.spencerk.ReimbursementAPI.repository")
public class MongoConfig {
    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(System.getenv("MONGO_CON_STR"));
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                .applyConnectionString(connectionString)
                                                                .build();

        return MongoClients.create(clientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), "fox-logistics");
    }
}
