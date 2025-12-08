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
            
            System.out.println("Sample equipment initialized! Use Equipment IDs: 1, 2, 3, 4");
        }
    }
    
    public static boolean isUsingDatabase() {
        return useDatabase;
    }
    
    public static List<String> getTransactionLog() {
        return transactionLog;
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