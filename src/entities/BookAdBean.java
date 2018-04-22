package entities;

import java.io.Serializable;

public class BookAdBean implements Serializable{
	private static final long serialVersionUID = -5850697870361203330L;

	private String bookid;
	private String booktitle;
	private String bookauthor;
	private String bookdesc;
	private String thumbnail;
	private String name;
	private int price;
	private int soldstatus;
	private String email;
	private int pin;
	private String city;
	private String area;
	private String buyerEmail;
	
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
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
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSoldstatus() {
		return soldstatus;
	}
	public void setSoldstatus(int soldstatus) {
		this.soldstatus = soldstatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public BookAdBean(String bookid, String booktitle, String bookauthor, String bookdesc, String thumbnail,
			String name, int price, int soldstatus, String email, int pin, String city, String area) {
		super();
		this.bookid = bookid;
		this.booktitle = booktitle;
		this.bookauthor = bookauthor;
		this.bookdesc = bookdesc;
		this.thumbnail = thumbnail;
		this.name = name;
		this.price = price;
		this.soldstatus = soldstatus;
		this.email = email;
		this.pin = pin;
		this.city = city;
		this.area = area;
	}
	public BookAdBean() {
		super();
	}
	public BookAdBean(String bookid, String booktitle, String bookauthor, String bookdesc, String thumbnail,
			String name, int price, int soldstatus, String email, int pin, String city, String area, String buyerEmail) {
		super();
		this.bookid = bookid;
		this.booktitle = booktitle;
		this.bookauthor = bookauthor;
		this.bookdesc = bookdesc;
		this.thumbnail = thumbnail;
		this.name = name;
		this.price = price;
		this.soldstatus = soldstatus;
		this.email = email;
		this.pin = pin;
		this.city = city;
		this.area = area;
		this.buyerEmail = buyerEmail;
	}
	@Override
	public String toString() {
		return "BookAdBean [bookid=" + bookid + ", booktitle=" + booktitle + ", bookauthor=" + bookauthor
				+ ", bookdesc=" + bookdesc + ", thumbnail=" + thumbnail + ", name=" + name + ", price=" + price
				+ ", soldstatus=" + soldstatus + ", email=" + email + ", pin=" + pin + ", city=" + city + ", area="
				+ area + "]";
	}
	
}
