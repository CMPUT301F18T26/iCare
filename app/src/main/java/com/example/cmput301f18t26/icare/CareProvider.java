package com.example.cmput301f18t26.icare;

import java.util.ArrayList;

public class CareProvider extends User{
    // Array list to store the patients
    private ArrayList<String> patientIds;

    // Actual constructor
    // No problems passed in
    CareProvider(String id, String username, String password, String email, String phone){
        super(id, username, password, email, phone);
        this.patientIds = new ArrayList<>();
        this.setUserType("careprovider");
    }

    // Problems passed in
    CareProvider(String id, String username, String password, String email, String phone, ArrayList<String> patientIds){
        super(id, username, password, email, phone);
        this.patientIds = patientIds;
        this.setUserType("careprovider");
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
