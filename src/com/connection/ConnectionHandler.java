package com.connection;

import java.sql.*;

public class ConnectionHandler {
	  private static Connection conn = null;

	  final private static String user = "root";

	  final private static String passwd = "root";
	  
	 public static Connection getConnection()
	  {
		  try {
			  	if(conn == null)
			  	{
			  		Class.forName("com.mysql.jdbc.Driver");
			  		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookDB", user, passwd);
			  	}
		  	}
		    catch (ClassNotFoundException e) {
		        System.out.println("Driver not found");
		        e.printStackTrace();
		    }
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
		  return conn;
	  }
	 public static Connection getConnectionWithoutDB()
	  {
		  try {
			  	if(conn == null)
			  	{
			  		Class.forName("com.mysql.jdbc.Driver");
			  		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", user, passwd);
			  	}
		  	}
		    catch (ClassNotFoundException e) {
		        System.out.println("Driver not found");
		        e.printStackTrace();
		    }
		    catch (SQLException e) {
		        e.printStackTrace();
		    }
		  return conn;
	  }
	   
	 public static void closeConnection()
	  {
		  try {
		      if (conn != null) {
		        conn.close();
		        conn = null;
		      }
		    } 
		  	catch (Exception e) {
		  		e.printStackTrace();
		    }
	  }
}
