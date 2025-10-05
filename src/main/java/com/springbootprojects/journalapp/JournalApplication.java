package com.springbootprojects.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // we need to add this Annotation if we are using Transaction Annotation in our project.
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    // PlatformTransactionManager is a Interface for handling Transaction Annotation
    // MongoTransactionManager is the impl for the above interface.
    @Bean
    public PlatformTransactionManager add (MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
