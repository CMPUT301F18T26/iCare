package com.example.cmput301f18t26.icare.Models;

import android.util.Log;

import com.example.cmput301f18t26.icare.Controllers.SearchController;

import java.util.List;
import java.util.UUID;

import io.searchbox.client.JestResult;

/**
 * Our user class could be a Patient or CareProvider. So, this class is abstract and cannot be initialized, Patient = 0, Care Provider = 1
 */
public abstract class User {
    private String UID; // let's make this immutable (its in caps cause convention)
    private String username;
    private String email;
    private String phone;
    private int role; // Patient = 0, Care Provider = 1

    /**
     * Our abstract class has a constructor that the subclasses may use as they share an
     * identical instantiation method.
     *
     * This is worth noting because the only difference between our superclass User and its
     * multiple subclasses are the methods that they implement. They are both instantiated with the
     * same set of initial properties.
     *
     * Abstract classes can not be instantiated, therefore this method is only for the purpose of
     * inheritance.
     *
     * On instantiation, we generate a unique id (UID) via the java UUID class for every user,
     * this id is persists with the user when saved to ElasticSearch.
     */
    public User(String username, String email, String phone, int role) {
        this.UID = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    /**
     * This method is used to validate the fields on a User object,
     *
     * If username, password, email, phone and role are set to acceptable inputs then it will
     * return true, false otherwise
     *
     * Encapsulating the logic for validating user within its class is a common pattern.
     * @return boolean
     */
    public boolean validate() {
        if (this.username.isEmpty() || this.username.length() < 8
                || this.email.isEmpty() || this.phone.isEmpty()) {
            // IF ANY TEXT FIELD IS EMPTY
            return false;
        } else if (this.role != 0 && this.role != 1 ) {
            // IF ROLE IS NOT 0 FOR PATIENT, 1 FOR CARE PROVIDER
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method is used to check if a username exists before signup.
     * @return
     */
    public boolean usernameTaken(){
        try{
            JestResult result = new SearchController.CheckIfUserNameExists().execute(username).get();
            // Hacky Patient thing
            Patient returnedUser = result.getSourceAsObject(Patient.class);
            // Here we check if we can fetch the UID, if not, then we get a NullPointerException, proving user does not exist.
            returnedUser.getUID();
        } catch (NullPointerException e){
            // If a null pointer exception was thrown, return false as username is not taken
            return false;
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
        // Assume its taken if NullPointerException was not caught
        return true;
    }

    /**
     * This method is used to change the email and phone of a user on ElasticSearch
     */
    public void updateUserInfo(){
        try {
            // Calling SearchController to change the user
            JestResult result = new SearchController.UpdateInformationForUser().execute(this).get();
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
    }

    /**
     * Fetches the information of a user with the matching uid.
     * @param uid
     */
    public static User fetchPatientInformation(String uid){
        User returnUser = null;
        try {
            // Getting the information on the user
            JestResult result = new SearchController.GetUserInfoUsingUId().execute(uid).get();
            // Now returning it as a User object
            returnUser = result.getSourceAsObject(Patient.class);
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
        return returnUser;
    }

    // Getter and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // We only require a getter for the uuid, it should not be mutable
    public String getUID() {
        return this.UID;
    }

    // We really shouldnt be able to set the UID but this is a quick fix for the login bug
    public void setUID(String uid) {
        this.UID = uid;
    }

    @Override
    public String toString(){
        return getUsername();
    }

}