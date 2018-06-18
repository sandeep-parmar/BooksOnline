package com.bean;

import java.io.Serializable;

public class Book implements Serializable{
	private static final long serialVersionUID = -451379059366096868L;
	private String booktitle;
	private String bookauthor;
	private String bookdesc;
	private String bookid;  //isbn
	private String thumbnail;
	
	private String category;
	private String isbn_10;
	private String publisher;
	private String published_date;
	
	public static String booktitleStr = "booktitle";
	public static String bookauthorStr = "bookauthor";
	public static String bookdescStr = "bookdesc";
	public static String bookidStr = "bookid";
	public static String thumbnailStr = "thumbnail";
	
	public static String categoryStr = "category";
	public static String isbn10Str = "isbn_10";
	public static String publisherStr = "publisher";
	public static String publisheddateStr = "published_date";
	
	
	public Book( String bookid, String booktitle, String bookauthor, String bookdesc, String thumbnail) {
		super();
		this.booktitle = booktitle;
		this.bookauthor = bookauthor;
		this.bookdesc = bookdesc;
		this.bookid = bookid;
		this.thumbnail = thumbnail;
	}

	public Book(String bookid, String booktitle, String bookauthor, String bookdesc, String thumbnail, String category,
			String isbn_10, String publisher, String published_date) {
		super();
		this.booktitle = booktitle;
		this.bookauthor = bookauthor;
		this.bookdesc = bookdesc;
		this.bookid = bookid;
		this.thumbnail = thumbnail;
		this.category = category;
		this.isbn_10 = isbn_10;
		this.publisher = publisher;
		this.published_date = published_date;
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

	public String getCategory() {		
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIsbn_10() {
		return isbn_10;
	}

	public void setIsbn_10(String isbn_10) {
		this.isbn_10 = isbn_10;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublished_date() {
		return published_date;
	}

	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}

	public static String getThumbnailStr() {
		return thumbnailStr;
	}

	public static void setThumbnailStr(String thumbnailStr) {
		Book.thumbnailStr = thumbnailStr;
	}

	@Override
	public String toString() {
		return "Book [booktitle=" + booktitle + ", bookauthor=" + bookauthor + ", bookdesc=" + bookdesc + ", bookid="
				+ bookid + ", thumbnail=" + thumbnail + ", category=" + category + ", isbn_10=" + isbn_10
				+ ", publisher=" + publisher + ", published_date=" + published_date + "]";
	}
	
	
}
