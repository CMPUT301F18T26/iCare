package com.example.cmput301f18t26.icare;

/**
 * Store app-wide constants and enums in this class
 */
public class Common {

    public enum Role {
        //enum types will be expanded once BodyLocation selection image has been decided on
        CAREPROVIDER ("Care Provider"),
        PATIENT ("Patient");

        public String role;

        Role(String role){
            this.role = role;
        }
    }
}
