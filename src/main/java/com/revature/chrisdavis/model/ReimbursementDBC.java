package com.revature.chrisdavis.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ReimbursementDBC {
	private String url = "jdbc:mariadb://database-0.cjzlpxmbn6he.us-east-2.rds.amazonaws.com:3306/mi6db";
	private String username = "mi6";
	private String password = "password";
	
	public Connection getDBC() throws SQLException{
		return DriverManager.getConnection(url, username, password);
	}
}
