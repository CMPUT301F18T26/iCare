package com.example.cmput301f18t26.icare.Models;

import com.example.cmput301f18t26.icare.Models.User;

import java.util.ArrayList;

/**
 * Patients have the role 0
 */
public class Patient extends User {

    private String careProviderUID;

    public Patient (String username, String password, String email, String phone, int role) {
        // Instantiate via our super-class method
        super(username, password, email, phone, role);
    }

    public String getCareProviderUID(){
        return this.careProviderUID;
    }

    public void setCareProviderUID(String careProviderUID){
        this.careProviderUID = careProviderUID;
    }

    // Editing the list of problems
    // Adding a new problem
    public void addProblem(String problemId){

    }

    // Deleting a problem
    public void deleteProblem(String problemID){

    }
}
