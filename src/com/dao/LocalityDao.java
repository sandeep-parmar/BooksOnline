package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.bean.Book;
import com.bean.Locality;
import com.connection.ConnectionHandler;

public class LocalityDao implements IBaseDao {

	public LocalityDao() {
		
	}
	
	public void saveLocality(final Locality locality)
	{
		/*PENDING*/
		/*Do not save if already exists*/
		 
		String sql = getInsertQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, locality.getPin());
			preparedStmt.setString(2, locality.getCity());
			preparedStmt.setString(3, locality.getArea());
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
	
	public Locality getLocality(int pin)
	{
		Locality locality = null;
		ResultSet rs = null;
		String sql = getSingleEntryQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setInt(1, pin);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				locality = new Locality(rs.getString("pin"), 
										rs.getString("city"),
										rs.getString("area")						
										);
			}
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return locality;
	}

	@Override
	public String getSelectQuery() {
		String sql = "select * from Locality";
		return sql;
	}

	@Override
	public String getInsertQuery() {
		String sql = "insert into LOCALITY(pin,city,area) values(?,?,?)";
		return sql;
	}

	@Override
	public String getSingleEntryQuery() {
		String sql = "select * from Locality where pin = ?";
		return sql;
	}

}
