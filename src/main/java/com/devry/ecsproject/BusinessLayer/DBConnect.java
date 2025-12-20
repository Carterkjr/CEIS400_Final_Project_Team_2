package com.devry.ecsproject.BusinessLayer;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class DBConnect {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static final String url = "jdbc:mysql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") + "/" + dotenv.get("DB_NAME") + "?user=" + dotenv.get("DB_USER") + "&password=" + dotenv.get("DB_PASSWORD");

    public DBConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connected to database successfully.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not connect to database");
        } 
    }

    public static void getData(String table, String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1)); // Example to print first column
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
        }
    }

    public static void saveData(String table, String userUpdate) {
        try {
            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Executing update: " + userUpdate);
            PreparedStatement update = conn.prepareStatement(userUpdate);
            int rowsAffected = update.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            conn.close();
            System.out.println("Data saved successfully to " + table);
            
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not save data to " + table + ". Error: " + e.getMessage());
        }
    }

    public static void deleteData(String table, String userDelete) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement delete = conn.prepareStatement(userDelete);
            delete.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not delete data from " + table);
        }
    }
    
    /**
     * Execute a query and return the ResultSet for processing
     * @param query The SQL query to execute
     * @return ResultSet containing the query results
     */
    public static ResultSet executeQuery(String query) {
        try {
            System.out.println("Executing query: " + query);
            Connection conn = DriverManager.getConnection(url);
            // Note: In a production system, you'd want to manage connections properly
            // For now, we'll keep the connection open for the ResultSet
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            return rs;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not execute query: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Execute a query and process results with a callback
     * This is a safer way to handle database queries
     */
    public static void executeQueryWithCallback(String query, java.util.function.Consumer<ResultSet> callback) {
        try {
            System.out.println("Executing query with callback: " + query);
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            callback.accept(rs);
            
            rs.close();
            statement.close();
            conn.close();
            System.out.println("Query executed successfully and connection closed");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not execute query: " + e.getMessage());
        }
    }

}
