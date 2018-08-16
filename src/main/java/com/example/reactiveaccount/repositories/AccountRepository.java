package com.example.reactiveaccount.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.example.reactiveaccount.entities.Account;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

	Mono<Account> findByUsername(String username);
	
	Account findUserByUsername(String username);
	
	Mono<Account> findByUsername(Mono<String> username);
}
