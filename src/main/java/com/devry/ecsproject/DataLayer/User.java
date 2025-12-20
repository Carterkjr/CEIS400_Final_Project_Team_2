//December 20 2025
//Team 2
//User class for authentication and user management

package com.devry.ecsproject.DataLayer;

import com.devry.ecsproject.BusinessLayer.DBConnect;
import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class User {
    private int userID;
    private String email;
    private String password;
    private int employeeID;
    private String role;
    private int accessLevel;
    
    // Database connection info
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static final String url = "jdbc:mysql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") + "/" + dotenv.get("DB_NAME") + "?user=" + dotenv.get("DB_USER") + "&password=" + dotenv.get("DB_PASSWORD");

    public User(int userID, String email, String password, int employeeID, String role, int accessLevel) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.employeeID = employeeID;
        this.role = role;
        this.accessLevel = accessLevel;
    }
    
    public User() {
        // Default constructor
    }
    
    // Create users table if it doesn't exist
    public static void createUsersTable() {
        try {
            Connection conn = DriverManager.getConnection(url);
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "userID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "email VARCHAR(255) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "employeeID INT, " +
                    "role VARCHAR(100), " +
                    "accessLevel INT DEFAULT 1" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(createTableQuery);
            stmt.executeUpdate();
            System.out.println("Users table created/verified successfully.");
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not create users table");
        }
    }
    
    // Check if default user exists, if not create it
    public static void ensureDefaultUserExists() {
        createUsersTable();
        
        try {
            Connection conn = DriverManager.getConnection(url);
            
            // Check if default user exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, "test.user@gbm.com");
            ResultSet rs = checkStmt.executeQuery();
            
            rs.next();
            int count = rs.getInt(1);
            
            if (count == 0) {
                // Create default user
                String insertQuery = "INSERT INTO users (email, password, employeeID, role, accessLevel) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, "test.user@gbm.com");
                insertStmt.setString(2, "1542"); // In production, this should be hashed
                insertStmt.setInt(3, 1001);
                insertStmt.setString(4, "Admin");
                insertStmt.setInt(5, 3);
                insertStmt.executeUpdate();
                System.out.println("Default user created: test.user@gbm.com");
            } else {
                System.out.println("Default user already exists.");
            }
            
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not create default user");
        }
    }
    
    // Authenticate user credentials
    public static User authenticateUser(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection(url);
            String query = "SELECT userID, email, password, employeeID, role, accessLevel FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getInt("userID"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("employeeID"),
                    rs.getString("role"),
                    rs.getInt("accessLevel")
                );
                conn.close();
                System.out.println("User authenticated successfully: " + email);
                return user;
            }
            
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Authentication failed for user: " + email);
        }
        
        return null; // Authentication failed
    }
    
    // Getters and Setters
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }
    
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public int getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}