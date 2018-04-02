package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.BookUser;
import com.connection.ConnectionHandler;

import entities.BookAdBean;

public class BooksFullAdDao implements IBaseDao{
	
	@Override
	public String getSelectQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInsertQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSingleEntryQuery() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getSelectQueryForTwoField(String field1, String field2) {
		String sql = "SELECT b.bookid, b.booktitle,b.bookauthor,b.bookdesc,b.thumbnail,bu.name, bu.price, bu.soldstatus, u.email, l.pin, l.city,l.area FROM book_user bu INNER JOIN books b ON (bu.bookid = b.bookid) INNER JOIN USER u ON (bu.uid = u.mobile) INNER JOIN locality l ON (bu.pin = l.pin) WHERE bu.uid =" + field1 + "  AND bu.bookid = " +field2;
		return sql;
	}

	public BookAdBean getBookFullDetails(String uid, String bookId){
		System.out.println(" getBookFullDetails called : uid"+uid+"  bookid"+bookId);
		BookAdBean bd = null;
		ResultSet rs = null;
		String queryTwo = getSelectQueryForTwoField(uid, bookId);
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(queryTwo);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				bd = new BookAdBean(rs.getString("bookid"),
									 rs.getString("booktitle"),
									 rs.getString("bookauthor"), 
									 rs.getString("bookdesc"), 
									 rs.getString("thumbnail"), 
									 rs.getString("name"), 
									 rs.getInt("price"),
									 rs.getInt("soldstatus"),
									 rs.getString("email"), 
									 rs.getInt("pin"),
									 rs.getString("city"), 
									 rs.getString("area")
									);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			ConnectionHandler.closeConnection();
		}
		return bd;
	}
}
