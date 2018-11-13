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
     */
    public User(String username, String password, String email, String phone, int role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
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
}