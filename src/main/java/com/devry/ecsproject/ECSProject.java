package com.devry.ecsproject;

import java.sql.Connection;
import java.sql.DriverManager;
import com.devry.ecsproject.BusinessLayer.DBConnect;
import com.devry.ecsproject.PresentationLayer.MainGUI;

/**
 *
 * @author ASUS
 */
public class ECSProject {

    public static void main(String[] args) {
        System.out.println("ECS system TEAM 2!");
        // Launch the main GUI
        MainGUI.main(args);
    }
}
