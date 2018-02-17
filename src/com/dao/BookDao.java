package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.bean.Book;
import com.connection.ConnectionHandler;


public class BookDao {

	public BookDao() {
		// TODO Auto-generated constructor stub
	}

	public static String saveBook(String bookid,String booktitle, String bookauthor,String bookdesc, String thumbnail)
	{
		System.out.println(" saveBook called with data : bookid : "+bookid+" booktitle : "+bookauthor +" bookdesc : "+bookdesc+" thumbnail : "+thumbnail);
		String sql = "insert into books(bookid,booktitle,bookauthor,bookdesc,thumbnail) values(?,?,?,?,?)";
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, bookid);
			preparedStmt.setString(2, booktitle);
			preparedStmt.setString(3, bookauthor);
			preparedStmt.setString(4, bookdesc);
			preparedStmt.setString(5, thumbnail);
			preparedStmt.execute();
			
		}catch (SQLIntegrityConstraintViolationException e) {
		   return bookid;
		}
		catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return bookid;
	}
	
	public static List<Book> getBookList()
	{
		List<Book> list = new ArrayList<>();
		ResultSet rs = null;
		String sql = "select * from books";
		//System.out.println("In getAllbed");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				Book book = new Book(rs.getString("booktitle"), 
									 rs.getString("bookauthor"),
									 rs.getString("bookdesc"),
									 rs.getString("bookid"),
									 rs.getString("thumbnail")
												);
				//System.out.println(bed.toString());
				list.add(book);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return list;
	}
}