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


public class BookDao implements IBaseDao{

	public BookDao() {
		// TODO Auto-generated constructor stub
	}

	public void saveBook(Book book)
	{
		/*PENDING*/
		/*Do not save if already exists + logging*/
		 
		String sql = getInsertQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, book.getIsbn());
			preparedStmt.setString(2, book.getTitle());
			preparedStmt.setString(3, book.getAuthors());
			preparedStmt.setString(4, book.getDescription());
			preparedStmt.setString(5, book.getThumbnail());
			preparedStmt.execute();
			
		}catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.toString());
		}
		catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
	}
	public Book geBook(String bookid) {
		Book book = null;
		ResultSet rs = null;
		String sql = getSingleEntryQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, bookid);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				book = new Book(rs.getString("booktitle"), 
									 rs.getString("bookauthor"),
									 rs.getString("bookdesc"),
									 rs.getString("bookid"),
									 rs.getString("thumbnail")
												);
			}
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return book;
	}
	
	public List<Book> getBookList()
	{
		List<Book> list = new ArrayList<Book>(0);
		ResultSet rs = null;
		String sql = getSelectQuery();
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

	@Override
	public String getSelectQuery() {
		String sql = "select * from books";
		return sql;
	}

	@Override
	public String getInsertQuery() {
		String sql = "insert into books(bookid,booktitle,bookauthor,bookdesc,thumbnail) values(?,?,?,?,?)";
		return sql;
	}

	@Override
	public String getSingleEntryQuery() {
		String sql = "select * from books where bookid = ?" ;
		return sql;
	}
	
}