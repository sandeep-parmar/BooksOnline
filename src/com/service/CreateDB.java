package com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.connection.ConnectionHandler;

public class CreateDB {

	public CreateDB() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		Connection conn = null;
		PreparedStatement pstmt=null;
		try {
			conn = ConnectionHandler.getConnectionWithoutDB();			
			
			/*delete database bookdb*/
			String sql = "drop database if exists  bookDB";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			/*Create database bookdb*/
			sql = "CREATE DATABASE IF NOT EXISTS bookDB";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			ConnectionHandler.closeConnection();
			
			/*create table books*/
			conn = ConnectionHandler.getConnection();
			sql = "create table books(bookid varchar(13) PRIMARY KEY, booktitle varchar(64) NOT NULL, bookauthor varchar(1024) NOT NULL, bookdesc varchar(2048) NOT NULL,thumbnail varchar(1024) NOT NULL)";
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
	}
}
