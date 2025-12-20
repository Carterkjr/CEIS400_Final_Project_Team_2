//November 28 2025
//Team 2
//In this file you can see the Employee definitions
//With Getter and Setters

package com.devry.ecsproject.DataLayer;

//Importing important libraries for variables like
//List and Data for Java Complex Control for History Based on the employee
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import com.devry.ecsproject.BusinessLayer.DBConnect;

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
    
    public void addEmployee() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(getHireDate());
            String fullName = getFirstName() + " " + getLastName();
            String email = getFirstName().toLowerCase() + "." + getLastName().toLowerCase() + "@company.com";
            
            String sqlQuery = "INSERT INTO employees (employeeID, name, email, hireDate, activeEmployee, role, accessLevel, department) VALUES (" + 
                             getEmployeeID() + ", '" + fullName + "', '" + email + "', '" + formattedDate + "', " + 
                             (isActiveEmployee() ? 1 : 0) + ", '" + getRole() + "', " + getAccessLevel() + ", '" + getDepartment() + "');";
            
            System.out.println("Executing SQL: " + sqlQuery);
            DBConnect.saveData("employees", sqlQuery);
            System.out.println("EMPLOYEE ADDED\n---------------------------\nEmpID: " + getEmployeeID() + "\nName: " + fullName + "\nRole: " + getRole());
        } catch (Exception e) {
            System.err.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void updateEmployee() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(getHireDate());
        String fullName = getFirstName() + " " + getLastName();
        String email = getFirstName().toLowerCase() + "." + getLastName().toLowerCase() + "@company.com";
        
        String sqlQuery = "UPDATE employees SET name = '" + fullName + "', email = '" + email + "', hireDate = '" + formattedDate + 
                         "', activeEmployee = " + (isActiveEmployee() ? 1 : 0) + ", role = '" + getRole() + 
                         "', accessLevel = " + getAccessLevel() + ", department = '" + getDepartment() + 
                         "' WHERE employeeID = " + getEmployeeID() + ";";
        DBConnect.saveData("employees", sqlQuery);
        System.out.println("EMPLOYEE UPDATED\n---------------------------\nEmpID: " + getEmployeeID() + "\nName: " + fullName);
    }

    // GETTERS AND SETTERS
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
    
    /**
     * Get an employee by ID from the database
     * @param employeeID The ID of the employee to retrieve
     * @return Employee object or null if not found
     */
    public static Employee getEmployeeByID(int employeeID) {
        try {
            final Employee[] result = {null}; // Use array to allow modification in lambda
            
            // Query the actual database
            String query = "SELECT * FROM employees WHERE employeeID = " + employeeID + " AND activeEmployee = 1";
            System.out.println("Searching for employee with query: " + query);
            
            DBConnect.executeQueryWithCallback(query, rs -> {
                try {
                    if (rs.next()) {
                        // Parse the result set and create Employee object
                        String name = rs.getString("name");
                        String[] nameParts = name.split(" ");
                        String firstName = nameParts.length > 0 ? nameParts[0] : "";
                        String lastName = nameParts.length > 1 ? nameParts[1] : "";
                        
                        Date hireDate = rs.getDate("hireDate");
                        boolean activeEmployee = rs.getBoolean("activeEmployee");
                        String role = rs.getString("role");
                        int accessLevel = rs.getInt("accessLevel");
                        String department = rs.getString("department");
                        
                        System.out.println("Found employee: " + name + ", Role: " + role + ", Department: " + department);
                        
                        result[0] = new Employee(employeeID, firstName, lastName, hireDate, activeEmployee, 
                                               role, accessLevel, department, new ArrayList<>());
                    } else {
                        System.out.println("Employee " + employeeID + " not found in database");
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing employee data: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            return result[0];
            
        } catch (Exception e) {
            System.err.println("Error getting employee by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Get all active employees from the database
     * @return List of all active employees
     */
    public static List<Employee> getAllEmployees() {
        try {
            List<Employee> employees = new ArrayList<>();
            
            // Query the actual database
            String query = "SELECT * FROM employees WHERE activeEmployee = 1 ORDER BY employeeID";
            System.out.println("Getting all employees with query: " + query);
            
            DBConnect.executeQueryWithCallback(query, rs -> {
                try {
                    while (rs.next()) {
                        int employeeID = rs.getInt("employeeID");
                        String name = rs.getString("name");
                        String[] nameParts = name.split(" ");
                        String firstName = nameParts.length > 0 ? nameParts[0] : "";
                        String lastName = nameParts.length > 1 ? nameParts[1] : "";
                        
                        Date hireDate = rs.getDate("hireDate");
                        boolean activeEmployee = rs.getBoolean("activeEmployee");
                        String role = rs.getString("role");
                        int accessLevel = rs.getInt("accessLevel");
                        String department = rs.getString("department");
                        
                        employees.add(new Employee(employeeID, firstName, lastName, hireDate, 
                                                 activeEmployee, role, accessLevel, department, new ArrayList<>()));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing employee list: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            System.out.println("Found " + employees.size() + " employees in database");
            return employees;
            
        } catch (Exception e) {
            System.err.println("Error getting all employees: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
