package com.example.cmput301f18t26.icare.Models;

import com.example.cmput301f18t26.icare.Models.User;

import java.util.ArrayList;

/**
 * Care Providers have the role 1
 */
public class CareProvider extends User {
    // Array list to store the patients
    private ArrayList<String> patientIds;

    public CareProvider(String username, String password, String email, String phone, int role) {
        // Instantiate via our super-class method
        super(username, password, email, phone, role);
        this.patientIds = new ArrayList<>();
    }

    // Getters and setters
    public ArrayList<String> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(ArrayList<String> patientIds) {
        this.patientIds = patientIds;
    }

    // Editing the list of problems
    // Adding a new problem
    public void addPatient(String patientId){
        this.patientIds.add(patientId);
    }

    // Deleting a problem
    public void deletePatient(String patientId){
        this.patientIds.remove(patientId);
    }
}
