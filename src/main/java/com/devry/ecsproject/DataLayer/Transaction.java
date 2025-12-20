/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.devry.ecsproject.DataLayer;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.devry.ecsproject.BusinessLayer.DBConnect;

/**
 *
 * @author Brittany Weisbeck
 */
public class Transaction {
    private int transactionID;
    private int equipmentID;
    private int employeeID;
    private String type;
    private Date transactionDate;
    
    // constructors
    public Transaction(int transactionID, int equipmentID, int employeeID, String type, Date transactionDate) {
        setTransactionID(transactionID);
        setEquipmentID(equipmentID);
        setEmployeeID(employeeID);
        setType(type);
        setTransactionDate(transactionDate);
    }

    public void recordTransaction(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(getTransactionDate());
        String sqlQuery = "INSERT INTO transactions (equipmentID, employeeID, type, transactionDate) VALUES (" + getEquipmentID() + ", " + getEmployeeID() + ", '" + getType() + "', '" + formattedDate + "');";
        DBConnect.saveData("transactions", sqlQuery);
        System.out.println("TRANSACTION RECORDED\n---------------------------\nEmpID: " + getEmployeeID() + "\nEquipID: " + getEquipmentID() + "\nType: " + getType());
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
    
    public int getEmployeeID() {
        return this.employeeID;
    }
    
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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
