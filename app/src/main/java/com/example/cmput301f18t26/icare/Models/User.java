package com.example.cmput301f18t26.icare.Models;

/**
 * Our abstract user class could be a Patient or CareProvider
 *
 * This class should ideally be instantiated with a builder pattern.
 */
public abstract class User {
    // Properties to store everything they need
    private String username;
    private String password;
    private String email;
    private String phone;
    private int role;
    private int id;

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
     * On another note, Users are not instantiated with a id (ElasticSearch Unique ID) field,
     * The uid for the object is assigned upon persisting to our ElasticSearch instance, this
     * happens in the DataController whenever a new user is created. We may treat all users without
     * an id field as invalid.
     */
    public User(String username, String password, String email, String phone, int role) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}