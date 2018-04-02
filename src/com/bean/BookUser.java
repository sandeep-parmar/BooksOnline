package com.bean;

import java.io.Serializable;

public class BookUser implements Serializable {

	Book book;
	Locality locality;
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	String uid;
	String bookid;
	int pin;
	
	String name;
	String phone;
	String price;
	int soldstatus;
	public BookUser() {
	}
	
	public BookUser(String uid, String bookid, int pin, String name, String phone, String price, int soldstatus) {
		super();
		this.uid = uid;
		this.bookid = bookid;
		this.pin = pin;
		this.name = name;
		this.phone = phone;
		this.price = price;
		this.soldstatus = soldstatus;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getSoldstatus() {
		return soldstatus;
	}
	public void setSoldstatus(int soldstatus) {
		this.soldstatus = soldstatus;
	}

	@Override
	public String toString() {
		return "BookUser [book=" + book + ", locality=" + locality + ", uid=" + uid + ", bookid=" + bookid + ", pin="
				+ pin + ", name=" + name + ", phone=" + phone + ", price=" + price + ", soldstatus=" + soldstatus + "]";
	}

	
	
}
