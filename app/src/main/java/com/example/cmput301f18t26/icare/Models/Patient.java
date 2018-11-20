package com.example.cmput301f18t26.icare.Models;

/**
 * A subclass of User. Meant to hold all the information for a Patient. Patients have the role 0
 */
public class Patient extends User {

    private String careProviderUID;

    /**
     * Constructor
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param role
     */
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

    /**
     * Editing the list of problems. Adding a new problem.
     * @param problemId
     */
    public void addProblem(String problemId){

    }

    /**
     * Editing the list of problems. Deleting a problem
     * @param problemID
     */
    public void deleteProblem(String problemID){

    }
}
