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


    public EmployeeGUIService() {
    }



    public Employee getEmployeeByID(int employeeID) {
        // TODO Get employe from database,  build employee object, and return.
        Date temp = new Date();
        List equipmentHistory = new ArrayList();
        Employee emp = new Employee(0,"John","Doe",temp,true,
                "Worker",1,"Maintenance",equipmentHistory);
        
        return emp;
    }
 

      public List getEmployeeEquipmentHistory(){
        // TODO Get employee from database and return department
        List<String> employeeEquipmentHistory;
        employeeEquipmentHistory = new ArrayList();
        return employeeEquipmentHistory;
    }

      
      public int employeeHired(Employee emp ){
        // TODO Add employee to database, create report, and return id code for success or failure
        int result = 0;
        return result;
    }

      
      public int employeeFired(Employee emp ){
        // TODO Remove employee from database, create report, and return id code for success or failure
        int result = 0;
        return result;
    }
} // end of EmployeeGUIService