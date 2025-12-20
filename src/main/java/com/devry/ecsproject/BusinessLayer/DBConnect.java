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
            conn.close();
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
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
        }
    }

    /**
     * Get data from a specific column and return as String
     * @param searchColumn The column number to retrieve (1-based)
     * @param table The table name for error reporting
     * @param userQuery The SQL query to execute
     * @return String containing the results, or null if error
     */
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
                } else {
                    result.append("\n").append(rs.getString(searchColumn));
                }
                resultingRows++;
            }
            conn.close();
            return result.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
            return null;
        }
    }

    /**
     * Get data from first column and return as String
     * @param userQuery The SQL query to execute
     * @return String containing the results, or error message
     */
    public static String getData(String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            StringBuilder searchResults = new StringBuilder();
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    searchResults.append("\n");
                }
                searchResults.append(rs.getString(1));
                first = false;
            }
            conn.close();
            return searchResults.toString();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data");
            return "Could not load data: " + e.getMessage();
        }
    }

    /**
     * Get data from a specific column by name and return as String
     * @param searchColumn The column name to retrieve
     * @param table The table name for error reporting
     * @param userQuery The SQL query to execute
     * @return String containing the results, or null if error
     */
    public static String getData(String searchColumn, String table, String userQuery) {
        try {
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement query = conn.prepareStatement(userQuery);
            ResultSet rs = query.executeQuery();
            StringBuilder searchResults = new StringBuilder();
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    searchResults.append("\n");
                }
                searchResults.append(rs.getString(searchColumn));
                first = false;
            }
            conn.close();
            return searchResults.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not load data from " + table);
            return null;
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
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not delete data from " + table);
        }
    }
    
    /**
     * Create a table in the database
     * @param tableName The name of the table for error reporting
     * @param createTableSQL The CREATE TABLE SQL statement
     * @return true if table was created successfully, false otherwise
     */
    public static boolean createTable(String tableName, String createTableSQL) {
        try {
            System.out.println("Creating table: " + tableName);
            Connection conn = DriverManager.getConnection(url);
            PreparedStatement statement = conn.prepareStatement(createTableSQL);
            statement.executeUpdate();
            statement.close();
            conn.close();
            System.out.println("Table '" + tableName + "' created successfully");
            return true;
        } catch(SQLException e) {
            // Check if table already exists
            if (e.getErrorCode() == 1050) { // MySQL error code for "table already exists"
                System.out.println("Table '" + tableName + "' already exists");
                return true;
            } else {
                e.printStackTrace();
                System.out.println("Could not create table '" + tableName + "': " + e.getMessage());
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not create table '" + tableName + "': " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Initialize database tables for the ECS system
     * Creates all necessary tables if they don't exist
     */
    public static void initializeTables() {
        System.out.println("Initializing ECS database tables...");
        
        // Create employees table (matching what Employee.java expects)
        String employeesTable = """
            CREATE TABLE IF NOT EXISTS employees (
                employeeID INT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                firstName VARCHAR(50),
                lastName VARCHAR(50), 
                hireDate DATE NOT NULL,
                activeEmployee BOOLEAN DEFAULT TRUE,
                role VARCHAR(100),
                accessLevel INT DEFAULT 1,
                department VARCHAR(100),
                email VARCHAR(100)
            )""";
        createTable("employees", employeesTable);
        
        // Create equipment table (matching what EquipmentGUIService expects)
        String equipmentTable = """
            CREATE TABLE IF NOT EXISTS equipment (
                equipmentID INT PRIMARY KEY,
                equipmentName VARCHAR(100) NOT NULL,
                category VARCHAR(50),
                type VARCHAR(50),
                isDamaged BOOLEAN DEFAULT FALSE,
                isAvailable BOOLEAN DEFAULT TRUE,
                description TEXT
            )""";
        createTable("equipment", equipmentTable);
        
        // Create transactions table for reports (foreign keys added separately to avoid conflicts)
        String transactionsTable = """
            CREATE TABLE IF NOT EXISTS transactions (
                transactionID INT AUTO_INCREMENT PRIMARY KEY,
                equipmentID INT,
                employeeID INT,
                transactionType VARCHAR(20) NOT NULL,
                transactionDate DATETIME NOT NULL,
                isDamaged BOOLEAN DEFAULT FALSE,
                notes TEXT,
                INDEX idx_equipment (equipmentID),
                INDEX idx_employee (employeeID),
                INDEX idx_date (transactionDate)
            )""";
        createTable("transactions", transactionsTable);
        
        // Add foreign key constraints after table creation to avoid dependency issues
        addForeignKeyConstraintsIfNeeded();
        
        System.out.println("Database table initialization complete");
    }
    
    /**
     * Add foreign key constraints safely after tables are created
     */
    private static void addForeignKeyConstraintsIfNeeded() {
        try {
            Connection conn = DriverManager.getConnection(url);
            
            // Check if foreign keys already exist
            PreparedStatement checkFK = conn.prepareStatement(
                "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'transactions' AND CONSTRAINT_TYPE = 'FOREIGN KEY'"
            );
            ResultSet rs = checkFK.executeQuery();
            rs.next();
            int fkCount = rs.getInt("count");
            
            if (fkCount == 0) {
                // Add foreign keys if they don't exist
                try {
                    PreparedStatement addEquipmentFK = conn.prepareStatement(
                        "ALTER TABLE transactions ADD CONSTRAINT fk_transactions_equipment " +
                        "FOREIGN KEY (equipmentID) REFERENCES equipment(equipmentID)"
                    );
                    addEquipmentFK.executeUpdate();
                    addEquipmentFK.close();
                    System.out.println("Equipment foreign key constraint added successfully");
                } catch (SQLException e) {
                    System.out.println("Equipment foreign key constraint may already exist: " + e.getMessage());
                }
                
                try {
                    PreparedStatement addEmployeeFK = conn.prepareStatement(
                        "ALTER TABLE transactions ADD CONSTRAINT fk_transactions_employee " +
                        "FOREIGN KEY (employeeID) REFERENCES employees(employeeID)"
                    );
                    addEmployeeFK.executeUpdate();
                    addEmployeeFK.close();
                    System.out.println("Employee foreign key constraint added successfully");
                } catch (SQLException e) {
                    System.out.println("Employee foreign key constraint may already exist: " + e.getMessage());
                }
            } else {
                System.out.println("Foreign key constraints already exist (" + fkCount + " found)");
            }
            
            checkFK.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Note: Could not add foreign key constraints: " + e.getMessage());
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