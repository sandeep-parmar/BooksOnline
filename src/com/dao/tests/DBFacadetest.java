package com.dao.tests;

import org.apache.shiro.util.Assert;

import com.bean.User;
import com.dao.DBFacade;
import com.dao.UserDao;

public class DBFacadetest {

	public DBFacadetest() {
		
	}
	
	void saveHundredBooks()
	{
		UserDao userDao = new UserDao();
		User user = userDao.getUserAccount("sandeepparmar20@gmail.com",User.emailStr);
		int status  = 0;
		int i = 0;
		for(i = 0; i < 100; i++)
		{
			 status = DBFacade.saveBookUser(user, "title"+i, 
										"author"+i, 
										"desc"+i, 
										"id"+i,
										"http://books.google.com/books/content?id=cqCiDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api", 										
										"pune", 
										"vishrantwadi", 
										"sandeep", 										
										"150");
			if(status != 0)
				break;
		}
		System.out.println("status:"+status+",i:"+i);
	}
	void deleteHundredBooks()
	{
		
	}
	public static void main(String[] args) {
		DBFacadetest test = new DBFacadetest();
		
		/**save 100 books in mysql db*/
		test.saveHundredBooks();
		
		/*delete aove saved 100 books from db*/
		test.deleteHundredBooks();
	}
}
