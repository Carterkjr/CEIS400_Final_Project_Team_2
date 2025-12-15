package com.devry.ecsproject;

import com.devry.ecsproject.PresentationLayer.MainGUI;

/**
 * ECSProject
 *
 * Official entry point for the Equipment Checkout System (ECS).
 * Delegates startup to the Presentation Layer (MainGUI), which launches
 * the Employee Login GUI.
 *
 * Added to address "how to login / how to run" clarity for Lab 6 testing.
 */
public class ECSProject {

    public static void main(String[] args) {
        System.out.println("Starting ECS System - Team 2");

        // Delegate to the Presentation Layer GUI entry point
        MainGUI.main(args);
    }
}
