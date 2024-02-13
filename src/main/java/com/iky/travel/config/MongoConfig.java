package com.iky.travel.config;

import static com.iky.travel.constant.common.MongoConstant.MONGO_DB_NAME;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

  private final MongoClient mongoClient;

  public MongoConfig(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoClient, MONGO_DB_NAME);
  }
}
