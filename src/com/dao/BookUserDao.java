package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.bean.BookUser;
import com.connection.ConnectionHandler;

public class BookUserDao implements IBaseDao {

	public BookUserDao() {
	}


	public String getSelectQueryForField(String field) {
		String sql = "select * from book_user where " + field + " = ?";
		return sql;
	}

	@Override
	public String getSelectQuery() {
		String sql = "select * from book_user where soldstatus = 0";
		return sql;
	}
	
	@Override
	public String getInsertQuery() {
		String sql = "insert into book_user(uid, bookid, pin, name, phone, price, soldstatus) values(?,?,?,?,?,?,?)";
		return sql;
	}
	
	public String getUpdateQuery() {
		String sql = "update book_user set soldstatus = 1 where uid = ? and bookid = ?";
		return sql;
	}
	public String getUpdatePriceQuery() {
		String sql = "update book_user set price = ? where uid = ? and bookid = ?";
		return sql;
	}

	public void saveBookUser(BookUser bookUser) {
		
		/*PENDING*/
		/*Do not savre if already exists*/
		 
		String sql = getInsertQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, bookUser.getUid());
			preparedStmt.setString(2, bookUser.getBookid());
			preparedStmt.setInt(3, bookUser.getPin());
			preparedStmt.setString(4, bookUser.getName());
			preparedStmt.setString(5, bookUser.getPhone());
			preparedStmt.setString(6, bookUser.getPrice());
			preparedStmt.setInt(7, bookUser.getSoldstatus());
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

	public List<BookUser> getBookList() {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		String sql = getSelectQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString("uid"),
												 rs.getString("bookid"),
												 rs.getInt("pin"), 
												 rs.getString("name"), 
												 rs.getString("phone"), 
												 rs.getString("price"), 
												 rs.getInt("soldstatus")
												 ); 
				list.add(bookUser);
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
	public String getSingleEntryQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BookUser> getBookListForUser(String uid) {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		String sql = getSelectQueryForField("uid");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, uid);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString("uid"),
												 rs.getString("bookid"),
												 rs.getInt("pin"), 
												 rs.getString("name"), 
												 rs.getString("phone"), 
												 rs.getString("price"), 
												 rs.getInt("soldstatus")
												 ); 
				list.add(bookUser);
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


	public void setSoldStatusTrue(String uid, String bookid) {
		String sql = getUpdateQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, uid);
			preparedStmt.setString(2, bookid);
			System.out.println(preparedStmt);
			preparedStmt.execute();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
	}
	
	public void updateNewOfferPrice(String uid, String bookid, String newprice) {		
		String sql = getUpdatePriceQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, newprice);
			preparedStmt.setString(2, uid);
			preparedStmt.setString(3, bookid);
			System.out.println(preparedStmt);
			
			preparedStmt.execute();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
	}

	public List<BookUser> getBookListByLocality(String city, String area) {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		String sql = getSelectQueryByLocality();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, "%"+city+"%");
			preparedStmt.setString(2, "%"+area+"%");
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString("uid"),
												 rs.getString("bookid"),
												 rs.getInt("pin"), 
												 rs.getString("name"), 
												 rs.getString("phone"), 
												 rs.getString("price"), 
												 rs.getInt("soldstatus")
												 ); 
				list.add(bookUser);
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


	private String getSelectQueryByLocality() {
		String sql = "select * from book_user where pin in(select pin from locality where city like ? and area like ?)";
		return sql;
	}	
}
