// December 1 2025
// Team 2
// This file will contain the service for connecting our database layer to our user interface
// Author : Jordan K

package com.devry.ecsproject.BusinessLayer;

// import System Control libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// import our employee class
import com.devry.ecsproject.DataLayer.Employee;

public class EmployeeGUIService {

    private static boolean dataInitialized = false;

    public EmployeeGUIService() {
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        if (!dataInitialized) {
            dataInitialized = true;
            System.out.println("Initializing sample employees in database...");
            
            // Add sample employees
            new Employee(123, "John", "Doe", new Date(), true, "Worker", 1, "Maintenance", new ArrayList<>()).addEmployee();
            new Employee(456, "Jane", "Smith", new Date(), true, "Supervisor", 2, "Operations", new ArrayList<>()).addEmployee();
            new Employee(789, "Bob", "Johnson", new Date(), true, "Worker", 1, "Maintenance", new ArrayList<>()).addEmployee();
            
            System.out.println("Sample employees initialized! Use Employee IDs: 123, 456, 789");
        }
    }



    public Employee getEmployeeByID(int employeeID) {
        // Return employee with the provided ID
        Date temp = new Date();
        List equipmentHistory = new ArrayList();
        Employee emp = new Employee(employeeID,"John","Doe",temp,true,
                "Worker",1,"Maintenance",equipmentHistory);
        return emp;
    }
 

      public List getEmployeeEquipmentHistory(){
        // TODO 
        // Get employee from database 
        // then return history
        List<String> employeeEquipmentHistory;
        employeeEquipmentHistory = new ArrayList();
        return employeeEquipmentHistory;
    }

      
      public int employeeHired(Employee emp ){
        System.out.println("Employee hired: " + emp.getFirstName() + " " + emp.getLastName());
        System.out.println("Role: " + emp.getRole() + ", Department: " + emp.getDepartment());
        int resultID = (int)(Math.random() * 10000) + 1;
        return resultID;
    }

      
      public int employeeFired(Employee emp ){
        System.out.println("Employee terminated: " + emp.getFirstName() + " " + emp.getLastName());
        System.out.println("Employee ID: " + emp.getEmployeeID());
        String queryString = "UPDATE employees SET activeEmployee = 0 WHERE employeeID = " + emp.getEmployeeID() + ";";
        DBConnect.saveData("employees", queryString);
        int resultID = (int)(Math.random() * 10000) + 1;
        return resultID;
    }
} // end of EmployeeGUIService
