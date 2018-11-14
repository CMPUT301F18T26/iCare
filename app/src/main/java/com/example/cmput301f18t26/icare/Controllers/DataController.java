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

    // The lone instance of our DataController
    private static DataController onlyInstance = null;

    private Gson gson = new Gson();
    private User currentUser = null;
    private List<User> userList = new ArrayList<>();


    // We use a private constructor here to enforce Singleton Pattern
    private DataController() {
        /**
         * When our lone instance of DataController is lazy loaded, lets also setup our
         * SearchController and instantiate its Jest Client which is also lazy loaded.
         */
        SearchController.setup();
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
    public String save() {

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

    /**
     *  Method for creating a new User on ElasticSearch
     *
     * @param user
     * @return user id string or null
     */
    public String addUser(User user){
        try {
            /**
             * Our response is a JestResult object after calling get(), we retrieve a JsonObject
             * from the JestResult and return the users's uid (equivalent to ElasticSearch's _id)
             */
            JsonObject jsonUser = new SearchController.AddUser().execute(user).get().getJsonObject();
            return jsonUser.get("_id").toString();
        } catch (Exception e) {
            Log.i("Error", "Failed to create the user", e);
            return null;
        }
    }

    public List<User> getPatients(String careProviderId){
        //return all patients for a care provider
        return null;
    }
}