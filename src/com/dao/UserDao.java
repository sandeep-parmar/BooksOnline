package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bean.User;
import com.connection.ConnectionHandler;
import com.utility.Errorcode;
import com.utility.UserField;

public class UserDao implements IBaseDao {

	public UserDao() {
		
	}

	public int saveUser(final User user) {
		int status = Errorcode.EC_SUCCESS.getValue();
		String sql = getInsertQuery();
		Connection conn = null;		
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getPassword());			
			preparedStmt.setString(3, user.getEmail());
			preparedStmt.setString(4, user.getSalt());
			preparedStmt.setString(5, user.getUuid());
			preparedStmt.setInt(6, user.getActive());
			preparedStmt.execute();
			
		}catch(SQLException e) {
			System.out.println(e.toString());
			status = Errorcode.EC_USER_REGISTRATION_FAILED.getValue();
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return status;
	}

	public boolean validate(User user) {
		ResultSet rs = null;
		
		boolean valid = false;
		String sql = getUserByFieldQuery(User.emailStr);
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, user.getEmail());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{		
				String password = rs.getString(User.passwordStr);
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
	public User getUserAccount(String fieldName, String fieldvalue)
	{
		ResultSet rs = null;
		User user = null;
		String sql = null;
		
		sql = getUserByFieldQuery(fieldName);
		
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, fieldvalue);
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{		
				user = new User(rs.getString(User.usernameStr),
						rs.getString(User.passwordStr),						
						rs.getString(User.emailStr),
						rs.getString(User.saltStr),
						rs.getString(User.uuidStr),
						rs.getInt(User.activeStr));
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

		

	public User isUserAccountActive(String field, String value) {
		ResultSet rs = null;
		User user = null;
		int status = -1;
		String sql = getUserByFieldQuery(field);

		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, value);
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			if(rs.next())
			{	
				user = new User(
						rs.getString("username"),					
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

	public void activateAccount(String uuid) {
		String sql = getUpdateQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, uuid);			
			System.out.println(preparedStmt.toString());
			preparedStmt.execute();
			
		}catch(SQLException e) {
		}
		finally {
			ConnectionHandler.closeConnection();
		}
	}

	public static int resetPassword(String uuid, String hashedPasswordBase64, String salt) {
		int status = Errorcode.EC_SUCCESS.getValue();
		String sql = getResetPasswordQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, hashedPasswordBase64);
			preparedStmt.setString(2, salt);
			preparedStmt.setString(3, uuid);			
			preparedStmt.execute();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			status = Errorcode.EC_RESET_PASSWORD_FAILED.getValue();
		}
		finally {
			ConnectionHandler.closeConnection();
		}	
		return status;
	}
	public int updateProfile(User olduser, User user) {
		int status = Errorcode.EC_SUCCESS.getValue();
		String sql = null;
		Connection conn = null;
		PreparedStatement preparedStmt = null;		
		try {
			conn = ConnectionHandler.getConnection();
			
			if(user.getUsername() != null)
			{
				sql = getProfileUpdateQuery(User.usernameStr);
				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setString(1, user.getUsername());			
				preparedStmt.setString(2, olduser.getUuid());
				preparedStmt.execute();
			}
/*			if(user.getMobile() != null)
			{
				sql = getProfileUpdateQuery(user.mobileStr);
				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setString(1, user.getMobile());			
				preparedStmt.setString(2, olduser.getUuid());
				preparedStmt.execute();
			}*/
			if(user.getEmail() != null)
			{
				sql = getProfileUpdateQuery(User.emailStr);
				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setString(1, user.getEmail());			
				preparedStmt.setString(2, olduser.getUuid());
				preparedStmt.execute();
				
				sql = getProfileUpdateQuery(User.activeStr);
				preparedStmt = conn.prepareStatement(sql);
				preparedStmt.setInt(1, 0);			
				preparedStmt.setString(2, olduser.getUuid());
				preparedStmt.execute();
			}
			
		}catch(SQLException e) {
			System.out.println(e.toString());
			status = Errorcode.EC_PROFILE_UPDATE_FAILED.getValue();
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return status;	
//		return 0;	
	}	
	
	private static String getResetPasswordQuery() {
		String sql = "update user set password=?, salt=? where uuid=?";
		return sql;
	}

	@Override
	public String getSelectQuery() {
		String sql = "select * from user";
		return sql;
	}

	@Override
	public String getInsertQuery() {
		String sql = "insert into USER(username,password,email,salt,uuid,active) values(?,?,?,?,?,?)";
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
		String sql = "update user set active = 1 where uuid = ?";
		return sql;
	}

	public static String getProfileUpdateQuery(String field)
	{
		String sql = "update user set " + field +"=?" + " where uuid = ?";
		System.out.println(sql);
		return sql;
	}
	@Override
	public String getSingleEntryQuery() {
		// TODO Auto-generated method stub
		return null;
	}
}
