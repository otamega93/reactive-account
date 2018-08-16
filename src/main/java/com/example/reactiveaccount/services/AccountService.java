package com.example.reactiveaccount.services;

import com.example.reactiveaccount.entities.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<Account> findByUsername(String username);
    
    Mono<Account> findByUsername(Mono<String> username);
    
    Mono<Account> findById(String id);

    Mono<Account> findById(Mono<String> id);
    
    Flux<Account> findAll();
    
    Mono<Account> deleteById(String id);

	Mono<Account> create(Mono<Account> accountMono);
	
	Mono<Account> update(String id, Account account);
}
