package com.example.cmput301f18t26.icare.Models;

/**
 * A subclass of User. Meant to hold all the information for a Patient. Patients have the role 0
 */
public class Patient extends User {

    private String careProviderUID;

    /**
     * Constructor
     * @param username
     * @param email
     * @param phone
     * @param careProviderUID
     */
    public Patient(String username, String email, String phone, String careProviderUID) {
        // Instantiate via our super-class method
        super(username, email, phone, 0);
        this.careProviderUID = careProviderUID;
    }

    public String getCareProviderUID(){
        return this.careProviderUID;
    }

    public void setCareProviderUID(String careProviderUID){ this.careProviderUID = careProviderUID; }
}
