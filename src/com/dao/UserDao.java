package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.Book;
import com.bean.User;
import com.connection.ConnectionHandler;
import com.utility.UserField;

public class UserDao {

	public UserDao() {
		// TODO Auto-generated constructor stub
	}

	public static int saveUser(final User user) {
		String sql = "insert into USER(username,password,mobile,email,salt,uuid,active) values(?,?,?,?,?,?,?)";
		Connection conn = null;
		int status = 0;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setString(3, user.getMobile());
			preparedStmt.setString(4, user.getEmail());
			preparedStmt.setString(5, user.getSalt());
			preparedStmt.setString(6, user.getUuid());
			preparedStmt.setInt(7, user.getActive());
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
	public static User getUserAccount(String fieldName, UserField field)
	{
		ResultSet rs = null;
		User user = null;
		String sql = null;
		switch(field)
		{
		case MOBILE:
			sql = "select * from USER where mobile=?";
			break;
		case USERNAME:
			sql = "select * from USER where username=?";
			break;
		case EMAIL:
			sql = "select * from USER where email=?";
			break;
		case UUID:
			sql = "select * from USER where uuid=?";
			break;
		}
		
		System.out.println("Hello sandeep ----------");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, fieldName);
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{		
				user = new User(rs.getString("username"),rs.getString("password"),rs.getString("mobile"),rs.getString("email"),rs.getString("salt"),
						rs.getString("uuid"), rs.getInt("active"));
				
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

	public static int isUserAccountActive(String mobile) {
ResultSet rs = null;
		
		int status = -1;
		String sql = "select * from USER where mobile=?";
		//System.out.println("In getAllbed");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, mobile);
			//System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{					
				status = rs.getInt("active");
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return status;
	}

	public static void activateAccount(String username) {
		String sql = "update user set active = 1 where username = ?";
		Connection conn = null;
		int status = 0;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, username);			
			preparedStmt.execute();
			
		}catch(SQLException e) {
			System.out.println(e.toString());
			status = -1;
		}
		finally {
			ConnectionHandler.closeConnection();
		}
	}

}
