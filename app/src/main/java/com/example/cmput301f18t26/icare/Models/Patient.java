package com.example.cmput301f18t26.icare.Models;

import com.example.cmput301f18t26.icare.Models.User;

import java.util.ArrayList;

/**
 * A subclass of User. This class stores all the details for a Patient User. Patients have the role 0
 */
public class Patient extends User {
    private String careProviderUID;
    private ArrayList<String> problemIds;

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
        problemIds = new ArrayList<String>();
    }

    /**
     * Edits the list of problems. Adding a new problem to the list of problems.
     * @param problemId
     */
    public void addProblem(String problemId){
        this.problemIds.add(problemId);
    }

    /**
     * Edits the list of problems. Deleting a problem to the list of problems.
     * @param problemId
     */
    public void deleteProblem(String problemId){
        int index = this.problemIds.indexOf(problemId);
        this.problemIds.remove(index);
    }

    // Setters and getters
    public ArrayList<String> getProblems(){
        return problemIds;
    }
}
