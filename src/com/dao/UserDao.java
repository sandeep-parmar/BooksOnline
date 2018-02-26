package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bean.User;
import com.connection.ConnectionHandler;
import com.utility.UserField;

public class UserDao implements IBaseDao {

	public UserDao() {
		
	}

	public int saveUser(final User user) {
		String sql = getInsertQuery();
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

	public boolean validate(User user) {
		ResultSet rs = null;
		
		boolean valid = false;
		String sql = getUserByFieldQuery("mobile");
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, user.getMobile());
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
	public User getUserAccount(String fieldName, String field)
	{
		ResultSet rs = null;
		User user = null;
		String sql = null;
		
		sql = getUserByFieldQuery(field);
		
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, fieldName);
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{		
				user = new User(rs.getString("username"),
						rs.getString("password"),
						rs.getString("mobile"),
						rs.getString("email"),
						rs.getString("salt"),
						rs.getString("uuid"),
						rs.getInt("active"));
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

		

	public User isUserAccountActive(String mobile) {
		ResultSet rs = null;
		User user = null;
		int status = -1;
		String sql = getUserByFieldQuery("mobile");

		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, mobile);
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{	
				user = new User(
						rs.getString("username"),
						rs.getString("mobile"),
						rs.getString("email"),
						rs.getInt("active")
						);
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

	public void activateAccount(String username) {
		String sql = getUpdateQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, username);			
			preparedStmt.execute();
			
		}catch(SQLException e) {
		}
		finally {
			ConnectionHandler.closeConnection();
		}
	}

	@Override
	public String getSelectQuery() {
		String sql = "select * from user";
		return sql;
	}

	@Override
	public String getInsertQuery() {
		String sql = "insert into USER(username,password,mobile,email,salt,uuid,active) values(?,?,?,?,?,?,?)";
		return sql;
	}
	
	public static String getUserByFieldQuery(String field)
	{
		String sql = "select * from USER where " + field + "= ? ";
		
		System.out.println(sql.toString());
		return sql;
	}
	public static String getUpdateQuery()
	{
		String sql = "update user set active = 1 where username = ?";
		return sql;
	}

	@Override
	public String getSingleEntryQuery() {
		// TODO Auto-generated method stub
		return null;
	}
}
