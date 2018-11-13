package com.example.cmput301f18t26.icare.Models;

import com.example.cmput301f18t26.icare.Models.User;

import java.util.ArrayList;

public class Patient extends User {
    // Array list to store problems for this user
    private ArrayList<String> problemIds;

    public Patient (String username, String password, String email, String phone, int role) {
        // Instantiate via our super-class method
        super(username, password, email, phone, role);
        this.problemIds = new ArrayList<>();
    }

    // Setters and Getters
    public ArrayList<String> getProblemIds() {
        return problemIds;
    }

    public void setProblemIds(ArrayList<String> problemIds) {
        this.problemIds = problemIds;
    }

    // Editing the list of problems
    // Adding a new problem
    public void addProblem(String problemId){
        this.problemIds.add(problemId);
    }

    // Deleting a problem
    public void deleteProblem(String problemID){
        this.problemIds.remove(problemID);
    }
}
