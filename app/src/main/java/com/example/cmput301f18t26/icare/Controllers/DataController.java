package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Patient;
import com.example.cmput301f18t26.icare.Problem;
import com.example.cmput301f18t26.icare.Record;
import com.example.cmput301f18t26.icare.CareProvider;
import com.example.cmput301f18t26.icare.User;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

/**
 * DataController is a Singleton class used for working with ElasticSearch data associated with
 * our app. An instance of it may be retrieved using DataController.getInstance();
 */
class DataController {

    // The lone instance of our DataController
    private static DataController onlyInstance = null;
    private final String elasticSearchURL = "http://cmput301.softwareprocess.es:8080/cmput301f18t26";
    private JestDroidClient jestClient;

    private User currentUser = null;
    private List<User> userList = new ArrayList<>();


    // We use a private constructor here to enforce Singleton Pattern
    private DataController() {
        jestClient = new JestDroidClient();
        fetch();
    }

    /**
     * Below are the public methods that should be used for interacting with data controller
     */

    // Use this method to access our DataController Instance
    public static DataController getInstance() {
        // Lazy load it
        if (onlyInstance == null) {
            onlyInstance = new DataController();
        }

        return onlyInstance;
    }

    // Fetch problems, records associated with currentUser from ElasticSearch to our cache
    public void fetch() {
    }

    // Returns a string which is the id of the object saved given by elastic search
    public String save(){

        return null;
    }

    public Record getRecord(String recordId){
        //get specific record
        return null;
    }

    public List<Record> getRecords(String problemId){
        //get all records associated with the problem
        return null;
    }

    public String addRecord(Record record){
        //add record and return new recordId
        return null;
    }

    public Problem getProblem(String problemid){
        //get specific Problem
        return null;
    }

    public List<Problem> getProblems(String patientId){
        //return all problems for a patient
        return null;
    }

    public String addProblem(Problem problem){
        //add Problem and return new problemId
        return null;
    }

    public User getUser(String userId){
        //get specific Id
        return null;
    }

    public String addUser(User user){
        //add user and return the user id
        return null;
    }

    public List<User> getPatients(String careProviderId){
        //return all patients for a care provider
        return null;
    }
}