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
			preparedStmt.setString(1, book.getBookid());
			preparedStmt.setString(2, book.getBooktitle());
			preparedStmt.setString(3, book.getBookauthor());
			preparedStmt.setString(4, book.getBookdesc());
			preparedStmt.setString(5, book.getThumbnail());
			preparedStmt.setString(6, book.getBookid());
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
				book = new Book(rs.getString(Book.booktitleStr), 
									 rs.getString(Book.bookauthorStr),
									 rs.getString(Book.bookdescStr),
									 rs.getString(Book.bookidStr),
									 rs.getString(Book.thumbnailStr)
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
				Book book = new Book(rs.getString(Book.booktitleStr), 
						 			rs.getString(Book.bookauthorStr),
						 			rs.getString(Book.bookdescStr),
						 			rs.getString(Book.bookidStr),
						 			rs.getString(Book.thumbnailStr)
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

	public List<String> getSuggestionList(String phrase, String field) {
		List<String> clist = new ArrayList<>();
		ResultSet rs = null;
		String sql = getStrListQuery(field);
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, "%" + phrase + "%");			
			//System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				clist.add(rs.getString(field));
			}
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return clist;
	}	
	
	
	private String getStrListQuery(String str) {
		String sql = "select distinct " + str + " from books where " + str +" like ?" ;
		return sql;
	}

	@Override
	public String getSelectQuery() {
		String sql = "select * from books";
		return sql;
	}

	@Override
	public String getInsertQuery() {
		String sql = "insert into books(bookid,booktitle,bookauthor,bookdesc,thumbnail) select * from(select ? as bid, ? as bt, ? as ba, ? as bd, ? as th) as tmp"
				+ " where not exists"
				+ " (select bookid from books where bookid = ?) limit 1";		
		return sql;
	}

	@Override
	public String getSingleEntryQuery() {
		String sql = "select * from books where bookid = ?" ;
		return sql;
	}

	public List<String> getSuggestionList(String phrase) {
		// TODO Auto-generated method stub
		return null;
	}
	
}