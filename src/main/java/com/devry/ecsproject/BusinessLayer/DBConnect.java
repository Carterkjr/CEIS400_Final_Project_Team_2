package com.devry.ecsproject.BusinessLayer;

import java.sql.*;

public class DBConnect {
    private static final String url = "jdbc:mysql://" + System.getenv("DB_USER") + ":" + System.getenv("DB_PASSWORD") + "@" + System.getenv("DB_HOST") + ":" + System.getenv("DB_PORT") + "/" + System.getenv("DB_NAME");;

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

    public static void loadData(String table, String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
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
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            PreparedStatement update = conn.prepareStatement(userUpdate);
            int rowsAffected = update.executeUpdate();
            System.out.println("Updated " + rowsAffected + " in " + table);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not save data to " + table);
        }
    }

    public static void deleteData(String table, String userDelete) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement delete = conn.prepareStatement(userDelete);
            int rowsAffected = delete.executeUpdate();
            System.out.println("Entries deleted.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not delete data from " + table);
        }
    }

}
