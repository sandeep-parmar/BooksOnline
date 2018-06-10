package com.dao;

import java.util.List;

import com.bean.Book;
import com.bean.BookUser;
import com.bean.User;

public class DBFacade {
	
	static BookDao bookDao = new BookDao();
	static BookUserDao bookUserDao = new BookUserDao();
	//static LocalityDao localityDao = new LocalityDao();
	static UserDao userDao = new UserDao();
	
	public DBFacade() {
	}

	public static void saveBook(Book book) {
		/*PENDING check if already exist*/
		
		bookDao.saveBook(book);
	}
	
	 public static List<Book> getBookList()
	 {		
		 List<Book> list = bookDao.getBookList();
		 return list;
	 }
	 
	 public static List<BookUser> getBookAdListByCriteria(String city, String area, String criteria)
	 {
		 List<BookUser> list = bookUserDao.getBookListByCriteria(city, area, criteria);
	 		
		 for (BookUser bookUser : list) {
			 Book book = bookDao.geBook(bookUser.getBookid());
			 //Locality locality = localityDao.getLocality(bookUser.getPin());
			 
			 bookUser.setBook(book);
			 //bookUser.setLocality(locality);
			 
			// System.out.println(bookUser);
		}
		 return list;

	 }
	 
	 /*Used in home page*/
	 public static List<BookUser> getBookAdList()
	 {		
		 List<BookUser> list = bookUserDao.getBookList();		 
		 for (BookUser bookUser : list) {		
			 Book book = bookDao.geBook(bookUser.getBookid());
		
			 //Locality locality = localityDao.getLocality(bookUser.getPin());
			 
			 bookUser.setBook(book);
			// bookUser.setLocality(locality);
			 
			// System.out.println(bookUser);
		}
		 return list;
	 }

	public static int saveUser(User user) {		
		int status = userDao.saveUser(user);
		return status;
	}

	public static User isUserAccountActive(String field, String value) {		
		User user = userDao.isUserAccountActive(field, value);
		return user;
	}

	public static User getUserAccount(String field, String value) {		
		User user = userDao.getUserAccount(field, value);
		return user;
	}

	public static void activateUserAccount(String uuid) {
		userDao.activateAccount(uuid);
	}

	/*Save model 1 info*/
	public static int saveBookUser(User user, String title, String author, String desc, String id, String thumbnail,
		String lcity, String larea, String lname, String offPrice) {
		
		System.out.println(user.toString());
		//System.out.println();
		/*create required entities*/
		Book book = new Book(title, author, desc, id, thumbnail);
		//Locality locality = new Locality(lpin,lcity, larea);
		BookUser bookUser = new BookUser(user.getEmail(), id, larea, lcity, lname, offPrice, 0);
		
		/*save book and locality details*/
		saveBook(book);
		//saveLocaity(locality);
		
		/*save composite details in book_user table*/	
		return bookUserDao.saveBookUser(bookUser);
	}
	
	public static List<BookUser> getMyBooks()
	{
		User user = UserRealm.getLoggedInUser();
		
		System.out.println(user.toString());
		
		List<BookUser> list = bookUserDao.getBookListForUser(user.getEmail());			
		 
		 for (BookUser bookUser : list) {
			 Book book = bookDao.geBook(bookUser.getBookid());
			 
			 bookUser.setBook(book);
			 			 
			// System.out.println(bookUser);
		}
		return list;
	}

	/*private static void saveLocaity(Locality locality) {
		
		/*PENDING check if already exist*/
		/*localityDao.saveLocality(locality);
	}*/

	public static int setSoldStatusTrue(String email, String bookId) {

		return bookUserDao.setSoldStatusTrue(email, bookId);
	}

	public static List<String> getCityList(String phrase) {
		return bookUserDao.getSuggestionList(phrase, BookUser.cityStr);
		
	}

	public static List<String> getLocalityList(String phrase) {		
		return bookUserDao.getSuggestionList(phrase, BookUser.areaStr);
	}

	public static int resetPassword(String uuid, String hashedPasswordBase64, String salt) {
		return UserDao.resetPassword(uuid, hashedPasswordBase64, salt);		
	}

	public static int updateNewOfferPrice(String email, String bookId, String newPrice) {
		return bookUserDao.updateNewOfferPrice(email, bookId, newPrice);
	}

	public static int updateProfile(User olduser, User user) {
		return userDao.updateProfile(olduser, user);		
	}

	public static List<String> getBookList(String phrase, String field) {
		return bookDao.getSuggestionList(phrase, field);
	}
}
