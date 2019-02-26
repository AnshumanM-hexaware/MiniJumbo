package com.example.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Practice {

	public static void main(String[] args) {

	}
	
	// Common JDBC and Mysql connection method
	private static  Connection DBConnection(String schema) {
	    Connection con = null;
		try{
	    	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection ("jdbc:mysql://172.25.227.164:3306/"+schema, "root", "Password123");
	    }catch (ClassNotFoundException e) {
	        System.out.println(e.getMessage());
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return con;
	}
	
	// Method to get the schema names.
	public static List<String> getSchema() throws ClassNotFoundException, SQLException {	
		Connection con = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection ("jdbc:mysql://172.25.227.164:3306", "root", "Password123");
		Statement st = null;
		ResultSet rs = null;
		String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA";
		List<String> schema = null;
		try {
			schema = new ArrayList<>();
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				schema.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schema;
	}
		
	// Method to get the table names.
	public static List<String> getTables(String schema) {
		Connection con = DBConnection(schema);
	    Statement st = null;
	    ResultSet rs = null;
	    String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA = '" + schema + "'";
	    List<String> tables = null;
	    try {
	    	tables = new ArrayList<>();
	        st = con.createStatement();
	        rs = st.executeQuery(query);
	        while (rs.next()) {
	        	tables.add(rs.getString(1));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();

	    }
	    return tables;
	}
	
	
	// Method to get the column names.
	public static List<String> getColumns(String schema, String table_name) {
		Connection con = DBConnection(schema);
	    Statement st = null;
	    ResultSet rs = null;
	    String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + schema + "' AND TABLE_NAME = '" + table_name + "'";
	    List<String> columns = null;
	    try {
	    	columns = new ArrayList<>();
	        st = con.createStatement();
	        rs = st.executeQuery(query);
	        while (rs.next()) {
	        	columns.add(rs.getString(1));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();

	    }
	    return columns;
	}
}
