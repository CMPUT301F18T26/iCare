package com.example.cmput301f18t26.icare.Models;

import java.util.ArrayList;

/**
 * A subclass of User. Meant to hold all the information for a Care provider. Care Providers have the role 1
 */
public class CareProvider extends User {
    /**
     * Constructor
     * @param username
     * @param email
     * @param phone
     */
    public CareProvider(String username, String email, String phone) {
        // Instantiate via our super-class method
        super(username, email, phone, 1);
    }
}
