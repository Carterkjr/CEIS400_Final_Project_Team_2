//November 28 2025
//Team 2
//In this file you can see the Employee definitions
//With Getter and Setters

package com.devry.ecsproject.DataLayer;

//Importing important libraries for variables like
//List and Data for Java Complex Control for History Based on the employee
import java.util.Date;
import java.util.List;

public class Employee {

    private int employeeID;
    private String firstName;
    private String lastName;
    private Date hireDate;
    private boolean activeEmployee;
    private String role;
    private int accessLevel;
    private String department;
    
    //This is an String Array, A list that contain a lot of Strings
    private List<String> equipmentHistory;

    public Employee(int employeeID, String firstName, String lastName, Date hireDate, boolean activeEmployee,
            String role, int accessLevel, String department, List<String> equipmentHistory) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.activeEmployee = activeEmployee;
        this.role = role;
        this.accessLevel = accessLevel;
        this.department = department;
        this.equipmentHistory = equipmentHistory;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isActiveEmployee() {
        return activeEmployee;
    }

    public void setActiveEmployee(boolean activeEmployee) {
        this.activeEmployee = activeEmployee;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getEquipmentHistory() {
        return equipmentHistory;
    }

    public void setEquipmentHistory(List<String> equipmentHistory) {
        this.equipmentHistory = equipmentHistory;
    }
}
