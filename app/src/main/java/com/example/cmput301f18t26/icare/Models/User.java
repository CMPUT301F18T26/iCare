package com.example.cmput301f18t26.icare.Models;

import java.util.UUID;

/**
 * Our user class could be a Patient or CareProvider
 *
 * Update - Make the User class non-abstract so that it can be instantiated to unpack data from ES
 *
 * This class should ideally be instantiated by a factory pattern.
 */
public class User {
    private String UID; // let's make this immutable (its in caps cause convention)
    private String username;
    private String password;
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
    public User(String username, String password, String email, String phone, int role) {
        this.UID = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
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
        if (this.username.isEmpty() || this.password.isEmpty() || this.email.isEmpty()
                || this.phone.isEmpty()) {
            // IF ANY TEXT FIELD IS EMPTY
            return false;
        } else if (this.role != 0 && this.role != 1 ) {
            // IF ROLE IS NOT 0 FOR PATIENT, 1 FOR CARE PROVIDER
            return false;
        } else {
            return true;
        }
    }

    // Getter and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}