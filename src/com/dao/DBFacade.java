package com.dao;

import java.util.List;

import com.bean.Book;
import com.bean.BookUser;
import com.bean.Locality;
import com.bean.User;

public class DBFacade {
	
	static BookDao bookDao = new BookDao();
	static BookUserDao bookUserDao = new BookUserDao();
	static LocalityDao localityDao = new LocalityDao();
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
			 Locality locality = localityDao.getLocality(bookUser.getPin());
			 
			 bookUser.setBook(book);
			 bookUser.setLocality(locality);
			 
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
			 Locality locality = localityDao.getLocality(bookUser.getPin());
			 
			 bookUser.setBook(book);
			 bookUser.setLocality(locality);
			 
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

	public static User getUserAccount(String principalAsEmail, String field) {		
		User user = userDao.getUserAccount(principalAsEmail, field);
		return user;
	}

	public static void activateUserAccount(String username) {
		userDao.activateAccount(username);
	}

	/*Save model 1 info*/
	public static void saveBookUser(User user, String title, String author, String desc, String id, String thumbnail, String lpin,
		String lcity, String larea, String lname, String lphno, String offPrice) {
		
		/*create required entities*/
		Book book = new Book(title, author, desc, id, thumbnail);
		Locality locality = new Locality(lpin,lcity, larea);
		BookUser bookUser = new BookUser(user.getMobile(), id, Integer.parseInt(lpin), lname, lphno, offPrice, 0);
		
		/*save book and locality details*/
		saveBook(book);
		saveLocaity(locality);
		
		/*save composite details in book_user table*/	
		bookUserDao.saveBookUser(bookUser);
	}
	
	public static List<BookUser> getMyBooks()
	{
		User user = UserRealm.getLoggedInUser();
		
		List<BookUser> list = bookUserDao.getBookListForUser(user.getMobile());			
		 
		 for (BookUser bookUser : list) {
			 Book book = bookDao.geBook(bookUser.getBookid());
			 
			 bookUser.setBook(book);
			 			 
			// System.out.println(bookUser);
		}
		return list;
	}

	private static void saveLocaity(Locality locality) {
		
		/*PENDING check if already exist*/
		localityDao.saveLocality(locality);
	}

	public static void setSoldStatusTrue(String mobile, String bookId) {

		bookUserDao.setSoldStatusTrue(mobile, bookId);
	}

	public static List<String> getCityList(String phrase) {
		return localityDao.getSuggestionList(phrase, "city");
		
	}

	public static List<String> getLocalityList(String phrase) {
		return localityDao.getSuggestionList(phrase, "area");
	}

	public static int resetPassword(String uuid, String hashedPasswordBase64, String salt) {
		return UserDao.resetPassword(uuid, hashedPasswordBase64, salt);		
	}

	public static void updateNewOfferPrice(String mobile, String bookId, String newPrice) {
		bookUserDao.updateNewOfferPrice(mobile, bookId, newPrice);
	}

	public static int updateProfile(User olduser, User user) {
		return userDao.updateProfile(olduser, user);		
	}

	public static List<String> getBookList(String phrase, String field) {
		return bookDao.getSuggestionList(phrase, field);
	}
}
