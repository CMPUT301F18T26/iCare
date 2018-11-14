package com.example.cmput301f18t26.icare.Controllers;

import android.util.Log;

import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.Record;
import com.example.cmput301f18t26.icare.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;

/**
 * DataController is used for caching and maintaining all persistent data associated with our app.
 * It interacts with ElasticSearchController to fetch and save data to our ElasticSearch instance.
 *
 * It acts as a middleware by saving relevant ElasticSearch data in its local DataStructures and
 * offering methods to maintain them.
 *
 * This class follows a Singleton design pattern in order to enforce all persistent data in
 * our app to come from a single place, sharing a the same instance.
 *
 * An instance of it may be retrieved via DataController.getInstance();
 */
public class DataController {

    /**
     * Do not touch the following, they enforce our design method and declare private fields
     */
    private static DataController onlyInstance = null; // the lone instance of our DataController

    private Gson gson = new Gson();
    private User currentUser = null;
    private List<User> userList = new ArrayList<>();
    private List<Problem> problemList = new ArrayList<>();

    /**
     * We use a private constructor here to enforce Singleton Pattern
     *
     * When our lone instance of DataController is lazy loaded, lets setup our SearchController
     * and instantiate its Jest Client which is also lazy loaded.
     */
    private DataController() {
        SearchController.setup();
    }

    /**
     * Below are the public methods that should be used for interacting with data controller
     */

    /**
     * Access the lone instance of our DataController
     * @return DataController
     */
    public static DataController getInstance() {
        // Lazy load it
        if (onlyInstance == null) {
            onlyInstance = new DataController();
        }

        return onlyInstance;
    }

    /**
     *  Adding a new user to the local users cache
     */
    public void addUser(User user) {
        userList.add(user);
        saveUser(user);
    }

    /**
     *  Grabbing all users in local users cache
     */
    public List<User> getUsers(){
        return userList;
    }

    /**
     *  Saving local users cache to ElasticSearch
     */
    public void saveUser(User user) {
        try {
            /**
             * Our response is a JestResult object after calling get(), we retrieve a JsonObject
             * from the JestResult and return the users's uid (equivalent to ElasticSearch's _id)
             */
            JsonObject jsonUser = new SearchController.AddUser().execute(user).get()
                    .getJsonObject();
            String userUID = jsonUser.get("_id").toString();
            Log.i("Created", userUID);
        } catch (Exception e) {
            Log.i("Error", "Failed to create the user", e);
        }
    }

    /**
     * Created by Conor to find the current user for use in Adding and Viewing Problems.
     * Need to create a new user everytime, and new username has to be zdrever becuase of this
     * method.
     * Quick fix and needs to be changed to work properly.
     * @return
     */
    public User getCurrentUser(){
        userList = getUsers();
        for (User each: userList){
            String username = each.getUsername();
            if (username.equals("zdrever")){
                currentUser = each;
            }
        }
        return currentUser;
    }

    /**
     *  Fetching users from ElasticSearch to local users cache
     */
    public void fetchUsers() {
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

    /**
     * Takes a user and returns all Problems from the whole problem list that belongs
     * to that user
     * @param user
     * @return
     */
    public List<Problem> getProblems(User user){
        //get the user UID of the user passed to the function
        String UID = user.getUID();
        //create a new problem list to be returned
        List<Problem> newProblemList = new ArrayList<>();
        //iterates through the whole problem list data base
        for (Problem each: problemList){
            String userUID = each.getUserUID();
            //if the UserUID of the problem in the problem list data base
            //matches the UID of the user passed to the function
            //save that problem to the problem list that is to be returned
            if (userUID.equals(UID)){
                newProblemList.add(each);
            }
        }
        //return all problems for a patient
        return newProblemList;
    }

    public void addProblem(Problem problem){
        Log.d("fuck", "here2");
        //add Problem and save
        problemList.add(problem);
        for (Problem each: problemList){
            String hi = each.getTitle();
            Log.d("fuck", hi);
        }
        //saveProblem(problem);
    }

    public void saveProblem(Problem problem) {
        try {
            /**
             * Our response is a JestResult object after calling get(), we retrieve a JsonObject
             * from the JestResult and return the users's uid (equivalent to ElasticSearch's _id)
             */
            JsonObject jsonProblem = new SearchController.AddProblem().execute(problem).get()
                    .getJsonObject();
            String problemPID = jsonProblem.get("_id").toString();
            Log.i("Created", problemPID);
        } catch (Exception e) {
            Log.i("Error", "Failed to create the problem", e);
        }
    }


    public List<User> getPatients(String careProviderId){
        //return all patients for a care provider
        return null;
    }
}