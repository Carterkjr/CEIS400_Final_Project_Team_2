// December 1 2025
// Team 2
// This file will contain the service for connecting our database layer to our user interface
// Author : Jordan K

package com.devry.ecsproject.BusinessLayer;

// import System Control libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            System.out.println("Initializing sample data in database...");
            
            // Add sample equipment
            new Equipment(1, "Cordless Drill", "Power Tools", false, true).addEquipment();
            new Equipment(2, "Ladder", "Safety Equipment", false, true).addEquipment();
            new Equipment(3, "Hammer", "Hand Tools", false, true).addEquipment();
            new Equipment(4, "Safety Vest", "Safety Equipment", false, true).addEquipment();
            
            // Add some sample transactions for testing
            addSampleTransactions();
            
            System.out.println("Sample equipment initialized! Use Equipment IDs: 1, 2, 3, 4");
        }
    }
    
    private void addSampleTransactions() {
        Date now = new Date();
        Date yesterday = new Date(now.getTime() - 24 * 60 * 60 * 1000);
        Date lastWeek = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
        
        addTransaction(1, 123, "checkout", lastWeek);
        addTransaction(2, 456, "checkout", yesterday);
        addTransaction(1, 123, "checkin", yesterday);
        addTransaction(3, 789, "checkout", now);
        
        System.out.println("Sample transactions added: " + transactionLog.size() + " transactions");
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
        
        // If no transactions found, add some sample data for demonstration
        if (transactionLog.isEmpty()) {
            System.out.println("No transactions found, adding sample transactions...");
            Date now = new Date();
            addTransaction(1, 123, "checkout", new Date(now.getTime() - 3 * 24 * 60 * 60 * 1000));
            addTransaction(2, 456, "checkout", new Date(now.getTime() - 2 * 24 * 60 * 60 * 1000));
            addTransaction(1, 123, "checkin", new Date(now.getTime() - 1 * 24 * 60 * 60 * 1000));
            addTransaction(3, 789, "checkout", now);
        }
        
        return transactionLog;
    }
    
    /**
     * Try to get transactions from the database
     */
    private static List<String> getTransactionsFromDatabase() {
        List<String> dbTransactions = new ArrayList<>();
        try {
            // This would normally query the database for transactions
            // For now, we'll simulate it
            // String query = "SELECT * FROM transactions ORDER BY transactionDate DESC";
            // Use DBConnect.executeQueryWithCallback to get real transactions
            
            System.out.println("Attempting to load transactions from database...");
            // TODO: Implement actual database query when Transaction table is properly set up
            
        } catch (Exception e) {
            System.err.println("Error querying database for transactions: " + e.getMessage());
        }
        return dbTransactions;
    }
    
    public static void addTransaction(int equipmentID, int employeeID, String type, Date date) {
        String transaction = "Equipment ID: " + equipmentID + " | Employee ID: " + employeeID + 
                           " | Type: " + type + " | Date: " + date.toString();
        transactionLog.add(transaction);
    }


    public Equipment getEquipmentByID(int equipmentID) {
        // Return equipment with the provided ID
        Equipment equipment;
        equipment = new Equipment(equipmentID,"Cordless Drill","Power Tools",false,true);
        return equipment;
    }
    
    public void setDamageStatus(boolean damaged){
        System.out.println("Equipment damage status updated to: " + damaged);
    }
    
    public void setAvailability(boolean available){
        System.out.println("Equipment availability updated to: " + available);
    }

   
} // end of EquipmentGUIService