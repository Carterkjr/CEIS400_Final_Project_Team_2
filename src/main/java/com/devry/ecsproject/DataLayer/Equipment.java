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
        String sqlQuery = "UPDATE equipment SET name = '" + getName() + "', type = '" + getType() + "', damage = " + isDamage() + ", available = " + isAvailable() + " WHERE equipmentID = " + getEquipmentID() + ";";
        DBConnect.saveData("equipment", sqlQuery);
    }

    public void getLastServiceDate() {
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
