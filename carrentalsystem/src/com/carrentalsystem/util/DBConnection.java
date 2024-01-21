package com.carrentalsystem.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
     private static Connection connection;

    public DBConnection() {
        
    }

    public static Connection getConnection() {
    	PropertyUtil pu=new PropertyUtil();
    	String url=pu.getPropertyString("crs_db.properties");        
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load the JDBC driver         
                connection = DriverManager.getConnection(url); 
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace(); // Handle exceptions according to your application's needs
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions according to your application's needs
            }
        }
    
}
  
}