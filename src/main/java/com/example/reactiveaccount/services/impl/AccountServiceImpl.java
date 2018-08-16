package com.example.reactiveaccount.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reactiveaccount.entities.Account;
import com.example.reactiveaccount.repositories.AccountRepository;
import com.example.reactiveaccount.services.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

	
	@Autowired
	private AccountRepository accountRepository;
	
	
	@Override
	public Mono<Account> findByUsername(String username) {
		
		return accountRepository.findByUsername(username);
	}

	@Override
	public Mono<Account> findByUsername(Mono<String> username) {
		
		return accountRepository.findByUsername(username);
	}

	@Override
	public Mono<Account> findById(String id) {
		
		return accountRepository.findById(id);
	}

	@Override
	public Mono<Account> findById(Mono<String> id) {
		
		return accountRepository.findById(id);
	}

	@Override
	public Flux<Account> findAll() {
		
		return accountRepository.findAll();
	}

	@Override
	public Mono<Account> deleteById(String id) {
		
		return accountRepository.findById(id)
	            .flatMap(oldUser -> 
	            accountRepository.deleteById(id)
	                               .then(Mono.just(oldUser))
	            );
	}

	@Override
	public Mono<Account> create(Mono<Account> userMono) {
		
		return userMono.map(newUser -> {
			
			Account user = new Account();
			
			if (null != newUser.getUsername()) {
			
				user.setUsername(newUser.getUsername());
			}
			
			if (null != newUser.getPassword()) {
				
				user.setPassword(newUser.getPassword());
			}
			
			return user;
			
		}).flatMap(accountRepository::save);
	}

	@Override
	public Mono<Account> update(String id, Account user) {
		
		return accountRepository.findById(id).map(existingUser -> {

            if(user.getUsername() != null){
            	existingUser.setUsername(user.getUsername());
            }
            if(user.getPassword() != null){
            	existingUser.setPassword(user.getPassword());
            }

            return existingUser;

        }).flatMap(accountRepository::save);
	}

}
