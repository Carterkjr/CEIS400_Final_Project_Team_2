//November 28 2025
//Team 2
//In this file you can see the Report definitions
//With Getter and Setters
package com.devry.ecsproject.DataLayer;

import com.devry.ecsproject.BusinessLayer.DBConnect;

//Importing Java Date Library, for Data manage
import java.util.Date;

public class Report {

    private int reportID;
    private String reportType;
    private Date date;
    private String reportText; 

    public Report(int reportID, String reportType, Date date, String reportText) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.date = date;
        this.reportText = reportText;
    }

    public void getReport() {
        String sqlQuery = "SELECT * FROM reports WHERE reportID = " + getReportID() + ";";
        DBConnect.getData("reports", sqlQuery);
        System.out.println("REPORT RETRIEVED: " + getReportID());
    }

    public void saveReport() {
        String sqlQuery = "INSERT INTO reports (reportType, date, reportText) VALUES ('" + getReportType() + "', '" + getDate() + "', '" + getReportText() + "');";
        DBConnect.saveData("reports", sqlQuery);
        System.out.println("REPORT SAVED: " + getReportID());
    }

    public void deleteReport() {
        String sqlQuery = "DELETE FROM reports WHERE reportID = " + getReportID() + ";";
        DBConnect.deleteData("reports", sqlQuery);
        System.out.println("REPORT DELETED: " + getReportID());
    }

    public int getReportID() {
        return reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public Date getDate() {
        return date;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }
}