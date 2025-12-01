// December 1 2025
// Team 2
// This file will contain the service for connecting our database layer to our user interface
// Author : Jordan K

package com.devry.ecsproject.BusinessLayer;

// import System Control libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// import our Report class
import com.devry.ecsproject.DataLayer.Report;

public class ReportGUIService {


    public ReportGUIService() {
    }
    
    public Report getReportByID(int reportID){
        // TODO get report from database from report's ID
        Date date = new Date();
        Report rep = new Report(0,"Equipment History Report",date,"Employee ID: 41083 checked out equipment on 10/14, 11/17, and 12/24");
        return rep;
    }
    
    public Report createReport(String reportType){
        // TODO create report from default template and save to DB
        Date date = new Date();
        Report rep = new Report(0,"Equipment History Report",date,"Employee ID: 41083 checked out equipment on 10/14, 11/17, and 12/24");
        return rep;
    }
   
} // end of EquipmentGUIService