package com.example.Mystruts;

import java.sql.Connection;
import java.sql.ResultSet;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Myaction {
	private String username;
	private String password;
	public String result="";

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String execute() {
		boolean res=	false;
		try {
			Connection con=DbConnection.getConnection();
			   PreparedStatement ps =(PreparedStatement) con.prepareStatement("select * from users where username=? and password=?");
			  	ps.setString(1, username);
			  	ps.setString(2, password);
			  	ResultSet rs =ps.executeQuery();
			  	res = rs.next();
			  	if(res== true) {
			  		result="success";
			  	}else {
			  		result="error";
			  	}
	          
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
}
}
