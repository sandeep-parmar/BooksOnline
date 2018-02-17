package entities;

import java.io.Serializable;

public class BookAd implements Serializable{

	private static final long serialVersionUID = -2433256321964390736L;

	private String userMobile;
	private String userEmail;
	private String isbn;
	private String offerPrice;
	private String preferredLoc;
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
	public BookAd(String userMobile, String userEmail, String isbn, String offerPrice, String preferredLoc) {
		super();
		this.userMobile = userMobile;
		this.userEmail = userEmail;
		this.isbn = isbn;
		this.offerPrice = offerPrice;
		this.preferredLoc = preferredLoc;
	}
	public BookAd() {
		super();
	}
	@Override
	public String toString() {
		return "BookAd [userMobile=" + userMobile + ", userEmail=" + userEmail + ", isbn=" + isbn + ", offerPrice="
				+ offerPrice + ", preferredLoc=" + preferredLoc + "]";
	}
	
}
