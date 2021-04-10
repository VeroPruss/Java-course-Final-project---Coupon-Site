package com.coupon.logic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLHandler {
	public static String db_address="localhost";
	public static String db_name="coupon_site_2";
	public static String db_user="root";
	public static String db_pass="";
	
	public static Connection conn;
	
	public static void connect2DB () throws ClassNotFoundException, SQLException
	{
		getConnection(db_address, db_name,db_user, db_pass);
	}
	
	public static void getConnection(String address,String db_name, String username, String password) throws ClassNotFoundException, SQLException
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + address + "/" + db_name,username, password);
//			conn.setAutoCommit(false);
		}
		catch (ClassNotFoundException cnfe) 
		{
			cnfe.printStackTrace();
			throw cnfe;
		}
		catch (SQLException se) 
		{
			se.printStackTrace();
			throw se;
		}
	}
	public static void closeConnetion() throws SQLException
	{
		try 
		{
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public static void execute(String sql) throws SQLException, ClassNotFoundException
	{
		connect2DB();
		Statement statement = null;
		try
		{
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		closeConnetion();
	}

	public static ResultSet run_query(String sql) throws SQLException
	{
		ResultSet res = null;
		try
		{
			Statement statement = conn.createStatement();
			res = statement.executeQuery(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw e;
		}
		return res;
	}
}
