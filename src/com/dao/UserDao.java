package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.Book;
import com.bean.User;
import com.connection.ConnectionHandler;

public class UserDao {

	public UserDao() {
		// TODO Auto-generated constructor stub
	}

	public static int saveUser(final User user) {
		String sql = "insert into USER(username,password,mobile,email,salt) values(?,?,?,?,?)";
		Connection conn = null;
		int status = 0;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, user.getusername());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setString(3, user.getMobile());
			preparedStmt.setString(4, user.getEmail());
			preparedStmt.setString(5, user.getSalt());
			preparedStmt.execute();
			
		}catch(SQLException e) {
			System.out.println(e.toString());
			status = -1;
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return status;
	}

	public static boolean validate(User user) {
		ResultSet rs = null;
		
		boolean valid = false;
		String sql = "select * from USER where mobile=?";
		//System.out.println("In getAllbed");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, user.getMobile());
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{		
			
				String password = rs.getString("password");
			
				if(password.equals(user.getPassword()))
				{
					valid = true;
				}
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return valid;
	}
	public static User getUserAccount(String username)
	{
		ResultSet rs = null;
		User user = null;
		String sql = "select * from USER where mobile=?";
		//System.out.println("In getAllbed");
		System.out.println("Hello sandeep ----------");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, username);
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{		
				user = new User(rs.getString("username"),rs.getString("password"),rs.getString("mobile"),rs.getString("email"),rs.getString("salt"));
				
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return user;
	}

}
