package com.example.Mystruts;

import java.sql.Connection;

import com.mysql.jdbc.PreparedStatement;

public class registar {
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
		try {
			Connection con=DbConnection.getConnection();
			PreparedStatement pst=(PreparedStatement) con.prepareStatement("insert into users(username,password)values(?,?)");
			pst.setString(1, getUsername());
			pst.setString(2, getPassword());
			int i = pst.executeUpdate();
			if(i>0) {
				result="success";
			}
			else {
				result="error";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;	
	}
	
}
