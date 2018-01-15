package com.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	@XmlElement 
	String username;
	@XmlElement
	String password;
	@XmlElement
	String mobile;
	@XmlElement
	String email;

	String salt;
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, String mobile, String email, String salt) {
		super();
		this.username = username;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
		this.salt = salt;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
		return "User [username=" + username + ", password=" + password + ", mobile=" + mobile + ", email=" + email
				+ ", salt=" + salt + "]";
	}

	
}
