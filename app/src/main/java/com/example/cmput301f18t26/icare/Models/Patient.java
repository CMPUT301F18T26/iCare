package com.example.cmput301f18t26.icare.Models;

import android.util.Log;

import com.example.cmput301f18t26.icare.Controllers.SearchController;

import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;

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

    /**
     * Sets the care provider for a patient and also, syncs this with the ES server.
     * @param careProviderUID
     */
    public void setCareProviderUID(String careProviderUID){
        // Updating data in this object
        this.careProviderUID = careProviderUID;
        // Updating data on ES
        try {
            JestResult result = new SearchController.UpdateInformationForUser().execute(this).get();
        } catch (Exception e) {
            Log.e("Error", "Could not add patient to your list in ES");
        }
    }
}
