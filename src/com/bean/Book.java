package com.bean;

public class Book {

	private String title;
	private String authors;
	private String description;
	private String isbn;
	private String thumbnail;
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(String title, String authors, String description, String isbn, String thumbnail) {
		super();
		this.title = title;
		this.authors = authors;
		this.description = description;
		this.isbn = isbn;
		this.thumbnail = thumbnail;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", authors=" + authors + ", description=" + description + ", isbn=" + isbn
				+ ", thumbnail=" + thumbnail + "]";
	}

	
}
