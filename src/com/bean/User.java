package com.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable{
	private static final long serialVersionUID = 3548737873219465459L;
	
	@XmlElement 
	String username;
	@XmlElement
	String password;
	@XmlElement
	String email;

	String salt;
	String uuid;
	int active;
	
	public static String usernameStr = "username";
	public static String passwordStr = "password";	
	public static String emailStr = "email";
	public static String saltStr = "salt";
	public static String uuidStr = "uuid";
	public static String activeStr = "active"; 
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, String password, String email, String salt, String uuid, int active) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.salt = salt;
		this.uuid = uuid;
		this.active = active;
	}

	
	public User(String username, String email, int active) {
		super();
		this.username = username;		
		this.email = email;
		this.active = active;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email
				+ ", salt=" + salt + ", uuid=" + uuid + ", active=" + active + "]";
	}
	
	
}