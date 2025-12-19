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

    public static String getData(int searchColumn, String table, String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            StringBuilder result = new StringBuilder();
            int resultingRows = 0;
            while(rs.next()) {
                if (resultingRows == 0) {
                    result.append(rs.getString(searchColumn));
                    resultingRows ++;
                } else {
                    result.append("\n").append(rs.getString(searchColumn)); // Example to print first column
                    resultingRows ++;
                }
            }
            return result.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
            return null;
        }
    }

    public static String getData(String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            StringBuilder searchResults = new StringBuilder();
            while (rs.next()) {
                searchResults.append("\n").append(rs.getString(1));
            }
            return searchResults.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data");
            return "Could not load data";
        }
    }

    public static String getData(String table, String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            StringBuilder searchResults = new StringBuilder();
            while (rs.next()) {
                searchResults.append("\n").append(rs.getString(1));
            }
            return searchResults.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
            return null;
        }
    }

    public static String getData(String searchColumn, String table, String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            StringBuilder searchResults = new StringBuilder();
            while (rs.next()) {
                searchResults.append("\n").append(rs.getString(searchColumn));
            }
            return searchResults.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
            return null;
        }
    }

    public static void saveData(String table, String userUpdate) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement update = conn.prepareStatement(userUpdate);
            update.executeUpdate();
            System.out.println("Successfully updated.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not save data to " + table);
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

}
