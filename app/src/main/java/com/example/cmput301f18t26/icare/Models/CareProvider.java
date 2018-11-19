package com.example.cmput301f18t26.icare.Models;

import com.example.cmput301f18t26.icare.Models.User;

import java.util.ArrayList;

/**
 * A sub class of user. This type of user is a Care Provider. Care Providers have the role 1.
 */
public class CareProvider extends User {

    /**
     * Constructor.
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
    public void addPatient(String patientId){

    }

    // Deleting a problem
    public void deletePatient(String patientId){
    }
}
