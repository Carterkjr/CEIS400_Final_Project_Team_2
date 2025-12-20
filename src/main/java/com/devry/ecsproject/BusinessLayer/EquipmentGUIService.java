// December 1 2025
// Team 2
// This file will contain the service for connecting our database layer to our user interface
// Author : Jordan K

package com.devry.ecsproject.BusinessLayer;

// import System Control libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

// import our equipment class
import com.devry.ecsproject.DataLayer.Equipment;

public class EquipmentGUIService {

    private static List<String> transactionLog = new ArrayList<>();
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static boolean useDatabase = "true".equalsIgnoreCase(dotenv.get("USE_DATABASE"));

    public EquipmentGUIService() {
        initializeSampleData();
    }
    
    private static boolean dataInitialized = false;
    
    private void initializeSampleData() {
        if (!dataInitialized && useDatabase) {
            dataInitialized = true;
            
            // First, initialize database tables
            DBConnect.initializeTables();
            
            System.out.println("Checking if sample equipment exists in database...");
            
            // Check if equipment already exists
            String checkQuery = "SELECT COUNT(*) as count FROM equipment";
            final int[] equipmentCount = {0};
            
            DBConnect.executeQueryWithCallback(checkQuery, (ResultSet rs) -> {
                try {
                    if (rs.next()) {
                        equipmentCount[0] = rs.getInt("count");
                    }
                } catch (SQLException e) {
                    System.err.println("Error checking equipment count: " + e.getMessage());
                }
            });
            
            if (equipmentCount[0] == 0) {
                System.out.println("No equipment found. Inserting sample equipment...");
                insertSampleEquipment();
            } else {
                System.out.println("Found " + equipmentCount[0] + " equipment records in database");
            }
            
            // Add some sample transactions for testing
            addSampleTransactions();
        }
    }
    
    /**
     * Insert sample equipment directly into database
     */
    private void insertSampleEquipment() {
        try {
            String[] equipmentInserts = {
                "INSERT INTO equipment (equipmentID, equipmentName, category, isDamaged, isAvailable) VALUES (1, 'Cordless Drill', 'Power Tools', false, true)",
                "INSERT INTO equipment (equipmentID, equipmentName, category, isDamaged, isAvailable) VALUES (2, 'Ladder', 'Safety Equipment', false, true)",
                "INSERT INTO equipment (equipmentID, equipmentName, category, isDamaged, isAvailable) VALUES (3, 'Hammer', 'Hand Tools', false, true)", 
                "INSERT INTO equipment (equipmentID, equipmentName, category, isDamaged, isAvailable) VALUES (4, 'Safety Vest', 'Safety Equipment', false, true)",
                "INSERT INTO equipment (equipmentID, equipmentName, category, isDamaged, isAvailable) VALUES (5, 'Wrench Set', 'Hand Tools', false, true)"
            };
            
            for (String sql : equipmentInserts) {
                DBConnect.saveData("equipment", sql);
            }
            
            System.out.println("Sample equipment inserted! Use Equipment IDs: 1, 2, 3, 4, 5");
            
        } catch (Exception e) {
            System.err.println("Error inserting sample equipment: " + e.getMessage());
        }
    }
    
    private void addSampleTransactions() {
        // Only add sample transactions if transactionLog is completely empty AND database has no transactions
        if (transactionLog.isEmpty() && useDatabase) {
            List<String> existingDbTransactions = getTransactionsFromDatabase();
            if (existingDbTransactions.isEmpty()) {
                System.out.println("No transactions found in database. Adding initial sample transactions...");
                
                Date now = new Date();
                Date yesterday = new Date(now.getTime() - 24 * 60 * 60 * 1000);
                Date lastWeek = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
                
                addTransaction(1, 123, "checkout", lastWeek);
                addTransaction(2, 456, "checkout", yesterday);
                addTransaction(1, 123, "checkin", yesterday);
                addTransaction(3, 789, "checkout", now);
                
                System.out.println("Sample transactions added: " + transactionLog.size() + " transactions");
            } else {
                System.out.println("Found existing transactions in database, skipping sample data");
            }
        } else if (!useDatabase) {
            System.out.println("Database disabled - using memory-only transactions");
        }
    }
    
    public static boolean isUsingDatabase() {
        return useDatabase;
    }
    
    public static List<String> getTransactionLog() {
        // If we have transactions in memory, return them
        if (!transactionLog.isEmpty()) {
            System.out.println("Returning " + transactionLog.size() + " transactions from memory");
            return transactionLog;
        }
        
        // Try to get transactions from database if using database
        if (useDatabase) {
            try {
                List<String> dbTransactions = getTransactionsFromDatabase();
                if (!dbTransactions.isEmpty()) {
                    transactionLog.addAll(dbTransactions);
                    System.out.println("Loaded " + dbTransactions.size() + " transactions from database");
                    return transactionLog;
                }
            } catch (Exception e) {
                System.err.println("Error loading transactions from database: " + e.getMessage());
            }
        }
        
        return transactionLog;
    }
    
    /**
     * Try to get transactions from the database
     */
    private static List<String> getTransactionsFromDatabase() {
        List<String> dbTransactions = new ArrayList<>();
        try {
            if (!useDatabase) {
                return dbTransactions;
            }
            
            System.out.println("Loading transactions from database...");
            String query = "SELECT t.transactionID, t.equipmentID, e.equipmentName, t.employeeID, " +
                          "emp.firstName, emp.lastName, t.transactionType, t.transactionDate " +
                          "FROM transactions t " +
                          "LEFT JOIN equipment e ON t.equipmentID = e.equipmentID " +
                          "LEFT JOIN employees emp ON t.employeeID = emp.employeeID " +
                          "ORDER BY t.transactionDate DESC";
            
            DBConnect.executeQueryWithCallback(query, (ResultSet rs) -> {
                try {
                    while (rs.next()) {
                        String transaction = "ID: " + rs.getInt("transactionID") + 
                                           " | Equipment: " + rs.getInt("equipmentID") + 
                                           " (" + (rs.getString("equipmentName") != null ? rs.getString("equipmentName") : "Unknown") + ")" +
                                           " | Employee: " + rs.getInt("employeeID") + 
                                           " (" + (rs.getString("firstName") != null ? rs.getString("firstName") + " " + rs.getString("lastName") : "Unknown") + ")" +
                                           " | Type: " + rs.getString("transactionType") + 
                                           " | Date: " + rs.getTimestamp("transactionDate");
                        dbTransactions.add(transaction);
                    }
                    System.out.println("Loaded " + dbTransactions.size() + " transactions from database");
                } catch (SQLException e) {
                    System.err.println("Error reading transaction results: " + e.getMessage());
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error querying database for transactions: " + e.getMessage());
        }
        return dbTransactions;
    }
    
    public static void addTransaction(int equipmentID, int employeeID, String type, Date date) {
        // Save to database if enabled
        if (useDatabase) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(date);
                
                String insertSQL = "INSERT INTO transactions (equipmentID, employeeID, transactionType, transactionDate) VALUES (" +
                                 equipmentID + ", " + employeeID + ", '" + type + "', '" + formattedDate + "')";
                
                DBConnect.saveData("transactions", insertSQL);
                System.out.println("Transaction saved to database: Equipment " + equipmentID + " " + type + " by Employee " + employeeID);
                
            } catch (Exception e) {
                System.err.println("Error saving transaction to database: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Also save to memory for current session
        String transaction = "Equipment ID: " + equipmentID + " | Employee ID: " + employeeID + 
                           " | Type: " + type + " | Date: " + date.toString();
        transactionLog.add(transaction);
    }


    public Equipment getEquipmentByID(int equipmentID) {
        if (!useDatabase) {
            // Return sample data for testing when database is disabled
            return new Equipment(equipmentID, "Sample Equipment", "Test Category", false, true);
        }
        
        try {
            System.out.println("Looking up equipment ID: " + equipmentID + " in database...");
            String query = "SELECT equipmentID, equipmentName, category, isDamaged, isAvailable FROM equipment WHERE equipmentID = " + equipmentID;
            
            final Equipment[] result = {null};
            
            DBConnect.executeQueryWithCallback(query, (ResultSet rs) -> {
                try {
                    if (rs.next()) {
                        int id = rs.getInt("equipmentID");
                        String name = rs.getString("equipmentName");
                        String type = rs.getString("category");
                        boolean damaged = rs.getBoolean("isDamaged");
                        boolean available = rs.getBoolean("isAvailable");
                        
                        result[0] = new Equipment(id, name, type, damaged, available);
                        System.out.println("Found equipment: " + name + " (ID: " + id + ")");
                    } else {
                        System.out.println("Equipment ID " + equipmentID + " not found in database");
                    }
                } catch (SQLException e) {
                    System.err.println("Error reading equipment data: " + e.getMessage());
                }
            });
            
            return result[0]; // Returns null if equipment not found
            
        } catch (Exception e) {
            System.err.println("Error retrieving equipment from database: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all equipment from the database
     * @return List of all equipment
     */
    public static List<Equipment> getAllEquipment() {
        List<Equipment> equipmentList = new ArrayList<>();
        
        if (!useDatabase) {
            // Return sample equipment for testing
            equipmentList.add(new Equipment(1, "Sample Drill", "Power Tools", false, true));
            equipmentList.add(new Equipment(2, "Sample Ladder", "Safety Equipment", false, true));
            return equipmentList;
        }
        
        try {
            String query = "SELECT equipmentID, equipmentName, category, isDamaged, isAvailable FROM equipment ORDER BY equipmentID";
            
            DBConnect.executeQueryWithCallback(query, (ResultSet rs) -> {
                try {
                    while (rs.next()) {
                        int id = rs.getInt("equipmentID");
                        String name = rs.getString("equipmentName");
                        String type = rs.getString("category");
                        boolean damaged = rs.getBoolean("isDamaged");
                        boolean available = rs.getBoolean("isAvailable");
                        
                        equipmentList.add(new Equipment(id, name, type, damaged, available));
                    }
                    System.out.println("Retrieved " + equipmentList.size() + " equipment items from database");
                } catch (SQLException e) {
                    System.err.println("Error reading equipment list: " + e.getMessage());
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error getting equipment list: " + e.getMessage());
        }
        
        return equipmentList;
    }
    
    /**
     * Get available equipment IDs as a formatted string for display
     * @return String with available equipment IDs and names
     */
    public static String getAvailableEquipmentInfo() {
        StringBuilder info = new StringBuilder();
        List<Equipment> equipment = getAllEquipment();
        
        if (equipment.isEmpty()) {
            return "No equipment available";
        }
        
        info.append("Available Equipment: ");
        for (int i = 0; i < equipment.size(); i++) {
            Equipment eq = equipment.get(i);
            if (i > 0) info.append(", ");
            info.append(eq.getEquipmentID()).append(" (").append(eq.getName()).append(")");
            
            if (info.length() > 200) { // Limit length for display
                info.append("...");
                break;
            }
        }
        
        return info.toString();
    }
    
    public void setDamageStatus(boolean damaged){
        System.out.println("Equipment damage status updated to: " + damaged);
    }
    
    public void setAvailability(boolean available){
        System.out.println("Equipment availability updated to: " + available);
    }

   
} // end of EquipmentGUIService