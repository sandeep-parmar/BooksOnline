package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.bean.Book;
import com.bean.BookAdDTO;
import com.connection.ConnectionHandler;

public class BookAdDAO {
	public BookAdDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<BookAdDTO> getBookAd(){
		List<BookAdDTO> bdList = new ArrayList<BookAdDTO>(0);
		ResultSet rs = null;
		String sql = "SELECT b.bookid,b.booktitle,b.bookauthor,b.bookdesc,b.thumbnail,ba.useremail,ba.usermobile,ba.offerprice,ba.preferredloc FROM books b RIGHT JOIN bookad ba ON(b.bookid=ba.isbn)";
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while(rs.next()){
				BookAdDTO bd = new BookAdDTO(rs.getString("usermobile"), 
									 rs.getString("useremail"),
									 rs.getString("bookid"),
									 rs.getString("offerprice"),
									 rs.getString("preferredloc"),
									 rs.getString("booktitle"),
									 rs.getString("bookauthor"),
									 rs.getString("bookdesc"),
									 rs.getString("thumbnail"));
				bdList.add(bd);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionHandler.closeConnection();
		}
		return bdList;
	}
	
	public static String saveBookAd(String isbn,String userMobile, String userEmail,String offerPrice, String preferredLoc)
	{
	System.out.println(" saveBookAd : isbn : "+isbn+" userMobile : "+userMobile +" userEmail : "+userEmail+" offerPrice : "+offerPrice+"preferredLoc:"+preferredLoc);
	String sql = "insert into bookad(isbn,userMobile,userEmail,offerPrice,preferredLoc) values(?,?,?,?,?)";
	Connection conn = null;
	try {
		conn = ConnectionHandler.getConnection();
		PreparedStatement preparedStmt = conn.prepareStatement(sql);
		preparedStmt.setString(1, isbn);
		preparedStmt.setString(2, userMobile);
		preparedStmt.setString(3, userEmail);
		preparedStmt.setString(4, offerPrice);
		preparedStmt.setString(5, preferredLoc);
		preparedStmt.execute();
		
	}catch (SQLIntegrityConstraintViolationException e) {
	   return isbn;
	}
	catch(SQLException e) {
		System.out.println(e.toString());
	}
	finally {
		ConnectionHandler.closeConnection();
	}
	return isbn;
}
}
