//November 28 2025
//Team 2
//In this file you can see the Equipment definitions
//With Getter and Setters

package com.devry.ecsproject.DataLayer;

import com.devry.ecsproject.BusinessLayer.DBConnect;

public class Equipment {

    private int equipmentID;
    private String name;
    private String type;
    private boolean damage;
    private boolean available;

    public Equipment(int equipmentID, String name, String type, boolean damage, boolean available) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.available = available;
    }

    public void updateEquipment() {
        String sqlQuery = "UPDATE equipment SET name = '" + getName() + "', type = '" + getType() + "', isDamaged = " + (isDamage() ? 1 : 0) + ", status = '" + (isAvailable() ? "available" : "checked out") + "' WHERE equipmentID = " + getEquipmentID() + ";";
        DBConnect.saveData("equipment", sqlQuery);
        System.out.println("EQUIPMENT UPDATED\n---------------------------\nEquipID: " + getEquipmentID() + "\nName: " + getName() + "\nType: " + getType() + "\nDamage: " + isDamage() + "\nAvailable: " + isAvailable());
    }
    
    public void addEquipment() {
        String sqlQuery = "INSERT INTO equipment (equipmentID, name, type, isDamaged, status) VALUES (" + 
                         getEquipmentID() + ", '" + getName() + "', '" + getType() + "', " + 
                         (isDamage() ? 1 : 0) + ", '" + (isAvailable() ? "available" : "checked out") + "');";
        DBConnect.saveData("equipment", sqlQuery);
        System.out.println("EQUIPMENT ADDED\n---------------------------\nEquipID: " + getEquipmentID() + "\nName: " + getName() + "\nType: " + getType());
    }

    public void getLastServiceDate() {
        System.out.println("LAST SERVICE DATE FOR " + getEquipmentID() + ": ");
        String sqlQuery = "SELECT MAX(transactionDate) FROM transactions WHERE equipmentID = " + getEquipmentID() + " AND type = 'maintenance';";
        DBConnect.getData("transactions", sqlQuery);
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isDamage() {
        return damage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
