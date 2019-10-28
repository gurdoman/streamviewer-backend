package com.adrianrossino.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {

	@Id
	private String userId;
	private String name;
	private String token;
	private String email;
	private String picture;
	private String familyName;
	
	public Users() {
		super();
	}

	public Users(String userId, String name, String token, String email, String picture, String familyName) {
		super();
		this.userId = userId;
		this.name = name;
		this.token = token;
		this.email = email;
		this.picture = picture;
		this.familyName = familyName;
	}
	
	public String getUser_id() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
}
