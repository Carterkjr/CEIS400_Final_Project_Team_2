/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.devry.ecsproject.DataLayer;

import java.util.Date;

/**
 *
 * @author Brittany Weisbeck
 */
public class Transaction {
    private int transactionID;
    private int equipmentID;
    private int userID;
    private String type;
    private Date transactionDate;
    
    // constructors
    public void Transaction(int transactionID, int equipmentID, int userID, String type, Date transactionDate) {
        setTransactionID(transactionID);
        setEquipmentID(equipmentID);
        setUserID(userID);
        setType(type);
        setTransactionDate(transactionDate);
    }
    
    // getters and setters
    public int getTransactionID() {
        return this.transactionID;
    }
    
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    
    public int getEquipmentID() {
        return this.equipmentID;
    }
    
    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
    
    public int getUserID() {
        return this.userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date date) {
        this.transactionDate = date;
    }
}
