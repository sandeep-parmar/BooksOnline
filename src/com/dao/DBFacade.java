package com.dao;

import java.util.List;

import com.bean.Book;
import com.bean.BookUser;
import com.bean.Locality;
import com.bean.User;

public class DBFacade {

	public DBFacade() {
	}

	public static void saveBook(Book book) {
		/*PENDING check if already exist*/
		BookDao dao = new BookDao();
		dao.saveBook(book);
	}
	
	 public static List<Book> getBookList()
	 {
		 BookDao dao = new BookDao();
		 List<Book> list = dao.getBookList();
		 return list;
	 }
	 
	 /*Used in home page*/
	 public static List<BookUser> getBookAdList()
	 {
		 BookUserDao dao = new BookUserDao();
		 List<BookUser> list = dao.getBookList();
		 
		 BookDao bDao = new BookDao();
		 LocalityDao lDao = new LocalityDao();
		 for (BookUser bookUser : list) {
			 Book book = bDao.geBook(bookUser.getBookid());
			 Locality locality = lDao.getLocality(bookUser.getPin());
			 
			 bookUser.setBook(book);
			 bookUser.setLocality(locality);
			 
			// System.out.println(bookUser);
		}
		 return list;
	 }

	public static int saveUser(User user) {
		UserDao dao = new UserDao();
		int status = dao.saveUser(user);
		return status;
	}

	public static User isUserAccountActive(String mobile) {
		UserDao dao = new UserDao();
		User user = dao.isUserAccountActive(mobile);
		return user;
	}

	public static User getUserAccount(String principalAsMobile, String field) {
		UserDao dao = new UserDao();
		User user = dao.getUserAccount(principalAsMobile, field);
		return user;
	}

	public static void activateUserAccount(String username) {
		UserDao dao = new UserDao();
		dao.activateAccount(username);
	}

	/*Save model 1 info*/
	public static void saveBookUser(User user, String title, String author, String desc, String id, String thumbnail,
		String lcity, String larea, String lpin, String lname, String lphno, String offPrice) {
		
		/*create required entities*/
		Book book = new Book(title, author, desc, id, thumbnail);
		Locality locality = new Locality(lpin, lcity, larea);
		BookUser bookUser = new BookUser(user.getMobile(), id, Integer.parseInt(lpin), lname, lphno, offPrice, 0);
		
		/*save book and locality details*/
		saveBook(book);
		saveLocaity(locality);
		
		/*save composite details in book_user table*/
		BookUserDao dao = new BookUserDao();
		dao.saveBookUser(bookUser);
	}
	
	public static List<BookUser> getMyBooks()
	{
		User user = UserRealm.getLoggedInUser();
		
		BookUserDao dao = new BookUserDao();
		List<BookUser> list = dao.getBookListForUser(user.getMobile());
		
		 BookDao bDao = new BookDao();
		 
		 for (BookUser bookUser : list) {
			 Book book = bDao.geBook(bookUser.getBookid());
			 
			 bookUser.setBook(book);
			 			 
			// System.out.println(bookUser);
		}
		return list;
	}

	private static void saveLocaity(Locality locality) {
		
		/*PENDING check if already exist*/
		LocalityDao ldao = new LocalityDao();
		ldao.saveLocality(locality);
	}

	public static void setSoldStatusTrue(String mobile, String bookId) {
		BookUserDao dao = new BookUserDao();
		dao.setSoldStatusTrue(mobile, bookId);
	}
}
