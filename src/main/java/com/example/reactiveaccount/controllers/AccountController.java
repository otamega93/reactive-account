package com.example.reactiveaccount.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactiveaccount.entities.Account;
import com.example.reactiveaccount.services.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping(value = "/{username}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE})
	public Mono<Account> reactiveFindByUsername(@PathVariable final String username) {

		return accountService
				.findByUsername(username);
	}
	
	@GetMapping(value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<Account> reactiveFindAll() {

		return accountService
				.findAll();
	}
	
    @PostMapping(value = "/", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<Account>> createReactiveAccount(@RequestBody @Valid final Mono<Account> user) {
    	
        return accountService
        		.create(user)
        		.map(content -> new ResponseEntity<>(content, HttpStatus.CREATED))
        		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(value = "/{id}", 
    		produces = {MediaType.APPLICATION_JSON_VALUE},
    		consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<Account>> updateReactiveAccount(@PathVariable final String id, 
    		@RequestBody @Valid Account userRequest) {
    	
    	return accountService
    			.update(id, userRequest)
    			.map(content -> new ResponseEntity<>(content, HttpStatus.OK))
    			.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    	
    }
    
    @DeleteMapping(value = "/{id}", 
    		produces = {MediaType.APPLICATION_JSON_VALUE},
    		consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<Account>> deleteReactiveAccount(@PathVariable final String id) {
    	
    	return accountService
    			.deleteById(id)
    			.map(content -> new ResponseEntity<Account>(HttpStatus.ACCEPTED))
    			.defaultIfEmpty(new ResponseEntity<Account>(HttpStatus.NOT_FOUND));
    }
}
