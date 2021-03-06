package com.example.reactiveaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableDiscoveryClient
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableReactiveMongoRepositories(basePackages= "com.example.reactiveaccount.repositories")
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
public class ReactiveAccountApplication extends AbstractReactiveMongoConfiguration {

    private final Environment environment;

    public ReactiveAccountApplication(Environment environment) {
        
    	this.environment = environment;
    }
    
	@Override
	@Bean
    @DependsOn("embeddedMongoServer")
	public MongoClient reactiveMongoClient() {
		 
		int port = environment.getProperty("spring.data.mongodb.port", Integer.class);
	    return MongoClients.create(String.format("mongodb://localhost:%d", port));
	}

	@Override
	protected String getDatabaseName() {
		
		return "reactive-mongo";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveAccountApplication.class, args);
	}
}
