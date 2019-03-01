package com.example.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	// Method to get the column names. sujay
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
	
	// Method to get the query
		public static List<String> getQuery(String schema, String table_name, String columns,  String keys) {
			String cols[] = columns.split(",");
			String key[] = keys.split(",");

		    /***************************Select * from Table*************************************/
			String query3="Select ";
			for(String a : cols) {
				query3 = query3 + a + ",";
			}
			query3 = query3.substring(0,query3.length()-1);
			query3 = query3 + " from " + schema + "." + table_name;
			String selectQuery = query3;
			
			/***************************Select count(*) from Table*************************************/
			String selectAll = "Select count(*) as count from "+schema+"."+table_name;
			
			/***************************Select Duplicates for multiple keys from Table*************************************/
			query3="Select ";
			String keyString = "";
			for(String a : key) {
				keyString = keyString + a + ",";
			}
			keyString = keyString.substring(0,keyString.length()-1);
			query3 = query3 + keyString + ", count(*) from " + schema + "." + table_name + " GROUP BY " + keyString + " having count(*)>1";
			String selectDuplicate = query3;
			
			
			/***************************Select Count of Duplicates from Table*************************************/
			String query4 = query3;
			query3 = "Select count(*) from (" + query4 + ") as cnt";
			String selectDuplicateCount = query3;
			
			/***************************Select Unique for multiple keys from Table*************************************/
			query3="Select ";
			keyString = "";
			for(String a : key) {
				keyString = keyString + a + ",";
			}
			keyString = keyString.substring(0,keyString.length()-1);
			query3 = query3 + keyString + ", count(*) from " + schema + "." + table_name + " GROUP BY " + keyString + " having count(*)=1";
			String selectUnique = query3;
			
			/***************************Select Unique records from Table*************************************/
			query3="Select distinct ";
			keyString = "";
			for(String a : key) {
				keyString = keyString + a + ",";
			}
			keyString = keyString.substring(0,keyString.length()-1);
			query3 = query3 + keyString + " from " + schema + "." + table_name;
			String selectUniqueRecords = query3;
			
			
			ArrayList<String> query = new ArrayList<String>();
			//query.add(selectQuery);
			query.add(selectAll);
			query.add(selectDuplicate);
			query.add(selectDuplicateCount);
			query.add(selectUnique);
			query.add(selectUniqueRecords);
			
		    /*
		    try {
		    	columns = new ArrayList<>();
		        st = con.createStatement();
		       // rs = st.executeQuery(query);
		        while (rs.next()) {
		        	query.add(rs.getString(1));
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();

		    }*/
		    return query;
		}
		
		public static Map<String, String> getPreview(String query) throws ClassNotFoundException, SQLException {
			Connection con = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection ("jdbc:mysql://172.25.227.164:3306", "root", "Password123");
			Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			query = query + " LIMIT 10";
			rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();	
            String column_name = "";
            int colsCnt = rsmd.getColumnCount();
            for(int i=1;i<=colsCnt;i++)
            	column_name = column_name + rsmd.getColumnName(i) + ",";
            column_name = column_name.substring(0,column_name.length()-1);
            //System.out.println(column_name); //column names
            String column_data = "";
            while (rs.next()) {
            	
            	for(int i=1;i<=colsCnt;i++) {
            		column_data = column_data + rs.getString(i) + ",";
            		if (column_data == null || column_data == "") {
                		column_data = null;
                	}
            	}
            	column_data = column_data.substring(0,column_data.length()-1);
            	column_data = column_data + "\n";
            	//System.out.println(column_data); //data
            }
            
            Map<String, String> result = new HashMap<>();
            result.put("cols", column_name);
            result.put("data", column_data);
            return result;
		}
	
	
}
