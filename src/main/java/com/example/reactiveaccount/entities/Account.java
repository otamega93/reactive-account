package com.example.reactiveaccount.entities;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Account {

	@Id
	private String id;
	
    @Indexed(unique=true)
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;

	public Account(String id, @NotBlank String username, @NotBlank String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Account() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
 
}
