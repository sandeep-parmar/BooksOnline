package com.bean;

import java.io.Serializable;

public class Book implements Serializable{
	private static final long serialVersionUID = -451379059366096868L;
	private String booktitle;
	private String bookauthor;
	private String bookdesc;
	private String bookid;  //isbn
	private String thumbnail;
	
	public static String booktitleStr = "booktitle";
	public static String bookauthorStr = "bookauthor";
	public static String bookdescStr = "bookdesc";
	public static String bookidStr = "bookid";
	public static String thumbnailStr = "thumbnail";
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(String title, String authors, String description, String isbn, String thumbnail) {
		super();
		this.booktitle = title;
		this.bookauthor = authors;
		this.bookdesc = description;
		this.bookid = isbn;
		this.thumbnail = thumbnail;
	}

	public Book(String bookid) {
		super();
		this.bookid = bookid;
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public String getBookauthor() {
		return bookauthor;
	}

	public void setBookauthor(String bookauthor) {
		this.bookauthor = bookauthor;
	}

	public String getBookdesc() {
		return bookdesc;
	}

	public void setBookdesc(String bookdesc) {
		this.bookdesc = bookdesc;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "Book [booktitle=" + booktitle + ", bookauthor=" + bookauthor + ", bookdesc=" + bookdesc + ", bookid="
				+ bookid + ", thumbnail=" + thumbnail + "]";
	}

	
}
