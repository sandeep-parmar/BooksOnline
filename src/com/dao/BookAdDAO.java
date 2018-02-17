package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.connection.ConnectionHandler;

public class BookAdDAO {

	public BookAdDAO() {
		// TODO Auto-generated constructor stub
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
