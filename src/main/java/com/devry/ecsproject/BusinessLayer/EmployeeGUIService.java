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
            System.out.println("Checking employee database...");
            
            // Only add sample employees if database is completely empty
            List<Employee> existingEmployees = getAllEmployees();
            if (existingEmployees == null || existingEmployees.isEmpty()) {
                System.out.println("No employees found in database. Adding initial sample employees...");
                
                // Add sample employees only if none exist
                new Employee(123, "John", "Doe", new Date(), true, "Worker", 1, "Maintenance", new ArrayList<>()).addEmployee();
                new Employee(456, "Jane", "Smith", new Date(), true, "Supervisor", 2, "Operations", new ArrayList<>()).addEmployee();
                new Employee(789, "Bob", "Johnson", new Date(), true, "Worker", 1, "Maintenance", new ArrayList<>()).addEmployee();
                
                System.out.println("Sample employees initialized! Use Employee IDs: 123, 456, 789");
            } else {
                System.out.println("Found " + existingEmployees.size() + " existing employees in database");
            }
        }
    }



    public Employee getEmployeeByID(int employeeID) {
        // Get employee from database
        Employee emp = Employee.getEmployeeByID(employeeID);
        if (emp != null) {
            return emp;
        } else {
            // Return null if employee not found
            return null;
        }
    }

    /**
     * Alternative simple method to get a test employee (for testing/demo purposes)
     * @param employeeID The employee ID
     * @return A test Employee object
     */
    public Employee getEmployeeByIDSimple(int employeeID) {
        // Return employee with the provided ID (test version)
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
        try {
            // Actually save the employee to the database
            emp.addEmployee();
            
            System.out.println("Employee hired: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Role: " + emp.getRole() + ", Department: " + emp.getDepartment());
            System.out.println("Employee ID: " + emp.getEmployeeID() + " saved to database");
            
            // Return the actual employee ID that was saved
            return emp.getEmployeeID();
        } catch (Exception e) {
            System.err.println("Error hiring employee: " + e.getMessage());
            return -1; // Return -1 to indicate error
        }
    }

    /**
     * Alternative simple hiring method (for testing/demo)
     * @param emp The employee to hire
     * @return A random ID for testing
     */
    public int employeeHiredSimple(Employee emp ){
        System.out.println("Employee hired: " + emp.getFirstName() + " " + emp.getLastName());
        System.out.println("Role: " + emp.getRole() + ", Department: " + emp.getDepartment());
        int resultID = (int)(Math.random() * 10000) + 1;
        return resultID;
    }

      
      public int employeeFired(Employee emp ){
        try {
            // Actually update the employee in the database
            emp.updateEmployee();
            
            System.out.println("Employee terminated: " + emp.getFirstName() + " " + emp.getLastName());
            System.out.println("Employee ID: " + emp.getEmployeeID() + " set to inactive in database");
            
            // Return the employee ID to indicate success
            return emp.getEmployeeID();
        } catch (Exception e) {
            System.err.println("Error terminating employee: " + e.getMessage());
            return -1; // Return -1 to indicate error
        }
    }

    /**
     * Alternative firing method using direct SQL (for testing/demo)
     * @param emp The employee to terminate
     * @return A random ID for testing
     */
    public int employeeFiredWithDirectSQL(Employee emp ){
        System.out.println("Employee terminated: " + emp.getFirstName() + " " + emp.getLastName());
        System.out.println("Employee ID: " + emp.getEmployeeID());
        String queryString = "UPDATE employees SET activeEmployee = 0 WHERE employeeID = " + emp.getEmployeeID() + ";";
        DBConnect.saveData("employees", queryString);
        int resultID = (int)(Math.random() * 10000) + 1;
        return resultID;
    }
    
    /**
     * Get all active employees from the database
     * @return List of all active employees
     */
    public List<Employee> getAllEmployees() {
        return Employee.getAllEmployees();
    }
    
    /**
     * Get the total count of active employees
     * @return Total number of active employees
     */
    public int getEmployeeCount() {
        List<Employee> employees = getAllEmployees();
        return employees != null ? employees.size() : 0;
    }
    
    /**
     * Generate a unique employee ID
     * @return A unique employee ID
     */
    public int generateEmployeeID() {
        // Generate a random ID between 1000 and 9999
        int newID;
        do {
            newID = (int)(Math.random() * 9000) + 1000;
        } while (getEmployeeByID(newID) != null); // Make sure ID is unique
        return newID;
    }
} // end of EmployeeGUIService
