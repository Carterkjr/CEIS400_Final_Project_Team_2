// December 1 2025
// Team 2
// This file will contain the service for connecting our database layer to our user interface
// Author : Jordan K

package com.devry.ecsproject.BusinessLayer;

// import System Control libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// import our equipment class
import com.devry.ecsproject.DataLayer.Equipment;

public class EquipmentGUIService {


    public EquipmentGUIService() {
    }


    public Equipment getEquipmentByID(int equipmentID) {
        // TODO Get equipment from database and return
        Equipment equipment;
        equipment = new Equipment(0,"Cordless Drill","Power Tools",false,true);
        return equipment;
    }
    
    public void setDamageStatus(boolean damaged){
        // TODO update damage status for equipment in DB
    }
    
    public void setAvailability(boolean available){
        // TODO update available status for equipment in DB
    }

   
} // end of EquipmentGUIService