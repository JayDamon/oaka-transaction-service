//package com.factotum.oaka.config;
//
//import com.mongodb.reactivestreams.client.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import java.io.IOException;
//
//@Profile({"h2"})
//@Configuration
//public class EmbeddedMongoConfig {
//
//    private static final String MONGO_DB_URL = "localhost";
//    private static final String MONGO_DB_NAME = "embeded_db";
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws IOException {
//        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
//        mongo.setBindIp(MONGO_DB_URL);
//        MongoClient mongoClient = mongo.getObject();
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
//        return mongoTemplate;
//    }
//
//}
