package com.cyam.grab2.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTools {
	/*private static final String url = "jdbc:jtds:sqlserver://10.150.127.184:1433/cyam";
	private static final String username = "yqrl";
	private static final String password = "abcd@1234";*/
	private static final String url = "jdbc:jtds:sqlserver://localhost:1433/cyam";
	private static final String username = "sa";
	private static final String password = "abcd@1234";
	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;
	
	//定义连接方法
	public static Connection getConn(){
		try {
			//1.加载驱动
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//2.建立连接
			connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//定义查询方法
	public static ResultSet query(String sql){
		try {
			//3.创建statement对象
			statement = getConn().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//4.执行查询
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//关闭连接
	public static void closeAll(){
		//6.关闭连接
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
