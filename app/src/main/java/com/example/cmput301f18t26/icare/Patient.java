package com.example.cmput301f18t26.icare;

import java.util.ArrayList;

// Class is not finished yet, will be finished before part 4
public class Patient extends User {
    // Array list to store problems for this user
    private ArrayList<String> problemIds;
    private String cardProviderId;

    // Actual constructor
    // No problems passed in
    Patient(String id, String username, String password, String email, String phone){
        super(id, username, password, email, phone);
        this.problemIds = new ArrayList<>();
        this.setUserType("patient");
    }

    // Problems passed in
    Patient(String id, String username, String password, String email, String phone, ArrayList<String> problemIds){
        super(id, username, password, email, phone);
        this.problemIds = problemIds;
        this.setUserType("patient");
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
