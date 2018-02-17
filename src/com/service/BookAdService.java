package com.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.dao.BookDao;

@Path("/bookAd")
public class BookAdService {

	public BookAdService() {
		// TODO Auto-generated constructor stub
	}
	@POST
	@Path("/saveBookAd")
	public void saveBook(@FormParam("userMobile") String userMobile,
			@FormParam("userEmail") String userEmail,
			@FormParam("offPrice") String offerPrice,
			@FormParam("prefLoc") String preferredLoc,
			@FormParam("isbn") String isbn
			)			
	{
		System.out.println("usermob"+userMobile+"userem"+userEmail+"offprice"+offerPrice+"prefloc"+preferredLoc+"isbn"+isbn);
		//System.out.println(title+", "+author+","+description+","+isbn+","+thumbnail);
		//BookDao.saveBook(bookid, booktitle, bookauthor, bookdesc, thumbnail);
	}
}
