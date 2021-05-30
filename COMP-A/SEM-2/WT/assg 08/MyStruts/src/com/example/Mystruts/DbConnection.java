package com.example.Mystruts;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

	private static Connection conn;
	
	static {
		try {
			  Class.forName("com.mysql.jdbc.Driver");
	          conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root","");
	          
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	
}
