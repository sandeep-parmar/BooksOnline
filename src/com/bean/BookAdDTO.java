package com.bean;

import java.io.Serializable;

public class BookAdDTO implements Serializable{

	private static final long serialVersionUID = -6489066352475721458L;
	private String userMobile;
	private String userEmail;
	private String isbn;
	private String offerPrice;
	private String preferredLoc;
	private String title;
	private String authors;
	private String description;
	private String thumbnail;
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getOfferPrice() {
		return offerPrice;
	}
	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}
	public String getPreferredLoc() {
		return preferredLoc;
	}
	public void setPreferredLoc(String preferredLoc) {
		this.preferredLoc = preferredLoc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BookAdDTO(String userMobile, String userEmail, String isbn, String offerPrice, String preferredLoc,
			String title, String authors, String description, String thumbnail) {
		super();
		this.userMobile = userMobile;
		this.userEmail = userEmail;
		this.isbn = isbn;
		this.offerPrice = offerPrice;
		this.preferredLoc = preferredLoc;
		this.title = title;
		this.authors = authors;
		this.description = description;
		this.thumbnail = thumbnail;
	}
	
	
}
