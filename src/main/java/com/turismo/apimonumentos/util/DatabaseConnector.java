package com.turismo.apimonumentos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	public static String URL_FORMAT="jdbc:mysql://%s:%s/%s"+
			"?useUnicode=true" + 
			"&useJDBCCompliantTimezoneShift=true" + 
			"&useLegacyDatetimeCode=false" + 
			"&serverTimezone=UTC";
	
	public static Connection getConnection() {
		String serverHost="localhost";
		String serverPort="3306";
		String databaseName="turismo";
		String userName="root";
		String password="penelope96";
		
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = String.format(URL_FORMAT, serverHost,serverPort,databaseName);
			conn= DriverManager.getConnection(url,userName,password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Fallo al realizar la conexion ");
		}
		
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fallo al cerrar la conexion");
		}
	}
	
//	public static int queryCreator(String query,String[..] args ) {
//		
//	}
	
}
