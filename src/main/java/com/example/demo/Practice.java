package com.example.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Practice {

	public static void main(String[] args) {
		System.out.println(getSchema());
		getTables("sakila");
	}
	
	
	public static List<String> getSchema() {
		List<String> schema = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// change user and password as you need it
		Connection con = null;
		try {
			con = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306", "root", "Password123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		schema = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = con.getMetaData().getCatalogs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				schema.add(rs.getString("TABLE_CAT"));
//			   schema = "TABLE_SCHEMA = " + rs.getString("TABLE_CAT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return schema;
	}
	
	public static List<String> getTables(String selschema) {
		List<String> tableList = null;
		try {
			String connection = null;
			connection = "jdbc:mysql://127.0.0.1:3306/" + selschema;
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(connection, "root", "Password123");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ResultSet rs= null;
			//
			// rs = con.getMetaData().getCatalogs();
			DatabaseMetaData md = con.getMetaData();
			ResultSet tabl = md.getTables(null, null, "%", null);
			tableList = new ArrayList<String>();
			while (tabl.next()) {
				tableList.add(tabl.getString(3));
			}
			// System.out.println(tableList);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tableList;

	}
}
