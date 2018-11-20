package com.example.cmput301f18t26.icare.Models;

import java.util.ArrayList;

/**
 * A subclass of User. Meant to hold all the information for a Care provider. Care Providers have the role 1
 */
public class CareProvider extends User {
    private ArrayList<String> patientIds;

    /**
     * Constructor
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param role
     */
    public CareProvider(String username, String password, String email, String phone, int role) {
        // Instantiate via our super-class method
        super(username, password, email, phone, role);
    }

    // Getters and setters
    public void getPatientIds() {

    }

    public void setPatientIds(ArrayList<String> patientIds) {

    }

    // Editing the list of problems
    // Adding a new problem

    /**
     * Editing the list of patients. Adds a new patient.
     * @param patientId
     */
    public void addPatient(String patientId){

    }

    /**
     * Editing the list of patients. Deletes a patient from the list..
     * @param patientId
     */
    public void deletePatient(String patientId){
    }
}
