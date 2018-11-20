package com.example.cmput301f18t26.icare.Controllers;

import android.util.Log;

import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.Record;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;
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
    private String currentProblem; // NOT THE OBJECT THIS IS A STRING
    private List<Patient> patientList = new ArrayList<>();
    private List<Problem> problemList = new ArrayList<>();
    private List<Record> recordList = new ArrayList<>();
    private List<Record> userRecordList = new ArrayList<>();

    /**
     * Used to change way I access ProblemUID's. This is a quick fix that will be changed before
     * final submission. For more details, check out saveUID() in DataController.
     */
    private String savedUID;
    /**
     * We use a private constructor here to enforce Singleton Pattern
     */
    private DataController() { }

    /*
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
     * Adding a new user to the local users cache
     * @param user
     */
    public void addUser(User user) {
        saveUser(user);
    }

    /**
     * Saving local users cache to ElasticSearch
     * @param user
     */
    public void saveUser(User user) {
        try {
            /*
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
     * Use the username and password to grab the appropriate user from ES and store
     * as the current user
     * @param username
     * @param password
     */
    public void logIn(String username, String password){
        try {
            JestResult result = new SearchController.SignInUser().execute(username, password).get();
             /*
             Unpack the user using the JestResult. Easier than unpacking the json object
             manually. To allow this, User had to be updated to not be an Abstract class.
             */

            User fetchedCurrentUser = result.getSourceAsObject(User.class);

            // check the role and unpack to the proper object so that no data is lost
            if (fetchedCurrentUser.getRole() == 0) {
                currentUser = result.getSourceAsObject(Patient.class);
            } else {
                currentUser = result.getSourceAsObject(CareProvider.class);
            }

        } catch (Exception e) {
            currentUser = null;
            Log.i("Error", "Problem talking to ES instance");
        }
    }

    /**
     * @return current user
     */
    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * @params current user
     */
    public void setCurrentUser(User user){
        currentUser = user;
    }

    /**
     * @param recordId
     * @return
     */
    public Record getRecord(String recordId){
        //get specific record
        return null;
    }

    /**
     * Get all records associated with a problem.
     * @param problem
     * @return
     */
    public List<Record> getRecords(Problem problem){
        //get all records associated with the problem
        String currentProblemID = problem.getUID();
        List<Record> newRecordList = new ArrayList<>();
        for (Record each: recordList){
            String problemID = each.getProblemId();

            if (problemID.equals(currentProblemID)){
                newRecordList.add(each);
            }
        }
        return newRecordList;
    }

    /**
     * Add record to the list of records maintained by this class.
     * @param record
     * @return
     */
    public String addRecord(Record record){
        //add record and return new recordId
        recordList.add(record);
        for (Record each: recordList){
            String title = each.getTitle();
        }
        //saveRecord(record);
        return null;
    }

    /**
     *
     * @param recordId
     * @return
     */
    public UserRecord getUserRecord(String recordId){
        //get specific record
        return null;
    }

    /**
     * Get records created by users associated with pr
     * @param problem
     * @return
     */
    public List<Record> getUserRecords(Problem problem){
        //get all records associated with the problem
        String currentProblemID = problem.getUID();
        List<Record> newUserRecordList = new ArrayList<>();
        for (Record record : this.userRecordList){
            String problemID = record.getProblemId();

            if (problemID.equals(currentProblemID)){
                newUserRecordList.add(record);
            }
        }
        return newUserRecordList;
    }

    /**
     * Adds a record to the list of records maintained by this class.
     * @param userRecord
     */
    public void addUserRecord(Record userRecord){
        //add record and return new recordId
        this.userRecordList.add(userRecord);
//        for (UserRecord each: this.userRecordList){
//            String title = each.getTitle();
//        }
        //saveUserRecord(userRecord);
        //return null;
    }

    /**
     * Returns a problem associated with a problemid.
     * @param problemid
     * @return
     */
    public Problem getProblem(String problemid){
        //get specific Problem
        Problem problem = null;
        for (Problem each: problemList) {
            String problemUID = each.getUID();
            if (problemUID.equals(problemid)){
                problem = each;
            }
        }
        return problem;
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

    /**
     * Adds a problem to the list of problems maintained by this class.
     * @param problem
     */
    public void addProblem(Problem problem){
        //add Problem and save
        problemList.add(problem);
        for (Problem each: problemList){
            String hi = each.getTitle();
        }
        //saveProblem(problem);
    }

    /**
     * Deletes the problem from the problemList
     * @param problem
     */
    public void deleteProblem(Problem problem){
        problemList.remove(problem);
    }

    /**
     * Updates the problemList.
     * List.set() takes an index and a new Object, and replaces the old Object at the index with the
     * new Object. I find the index of the OldProblem, and replace the OldProblem with the
     * NewProblem.
     * @param oldProblem
     * @param newProblem
     */
    public void updateProblem(Problem oldProblem, Problem newProblem){
        int index = problemList.indexOf(oldProblem);
        problemList.set(index, newProblem);
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

    /*
    Bad way of saving a UID temporarily to pass between activities. Will do a much better fix
     * before final submission. Allows my Save() in AddEditProblemActivity to save the
     * problem's UID temporarily, which allows me to access it in another activity.
     * This kind of replaces i.getSerializableExtra when passing intents between activites.
     * This wasn't updating for me properly so I made a quick fix, but for the final
     * submission we will have ElasticSearch working and this will not be necessary.
     */

    /**
     * Saves a UID.
     * @param UID
     */
    public void saveUID(String UID){
        this.savedUID = UID;
    }

    /**
     * Gets the saved UID
     * @return
     */
    public String getSavedUID(){
        return this.savedUID;
    }

    /**
     * Sets the passed in problemID to the to the curentProblem id.
     * @param problemID
     */
    public void setCurrentProblem(String problemID) {
        this.currentProblem = problemID;
    }

    /**
     * Returns the currentProblem saved in this class.
     * @return
     */
    public String getCurrentProblem() {
        return this.currentProblem;
    }

    /**
     * Fetch patients and then return the patient list
     * @return List<Patient>
     */
    public List<Patient> getPatients(){
        //return all patients for a care provider
        if (patientList.isEmpty()){
            fetchPatients();
        }
        return patientList;
    }

    /**
     * Fetch patients associated with this.currentUser from ElasticSearch and store them in the user list.
     */
    private void fetchPatients(){
        try {
            patientList = new SearchController.GetPatients().execute(currentUser.getUID()).get();
        } catch (Exception e) {
             Log.i("Error", "Could not get the list of patients associated to this care provider");
        }
    }

    /**
     * Search the patients by username and return the top hits as an ArrayList<User>
     * @param username
     * @return
     */
    public List<Patient> searchPatients(String username){
        try {
            return new SearchController.SearchPatients().execute(username).get();
        } catch (Exception e) {
            Log.i("Error", "Problem getting patients with username: " + username);
            return new ArrayList<>();
        }
    }

    /**
     * Add the patient the locally held patient list
     * @param patient
     */
    public void addPatientToPatientList(Patient patient){
        patientList.add(patient);
    }

    /**
     * Fetches the information of a user with the matching uid.
     * @param uid
     */
    public User fetchUserInformation(String uid){
        User returnUser = null;
        try {
            // Getting the information on the user
            JestResult result = new SearchController.GetUserInfoUsingUId().execute(uid).get();
            // Now returning it as a User object
            returnUser = result.getSourceAsObject(User.class);
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
        return returnUser;
    }

    /**
     * Method was created to change the email or phone number of a user the elastic search.
     * @param modifiedUser
     */
    public void updateElasticSearchForNewUserInfo(User modifiedUser){
        try {
            // Calling SearchController to change the user
            JestResult result = new SearchController.UpdateInformationForUser().execute(modifiedUser).get();
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
        }
    }

    /**
     * Method was created to check if a username exists before signup.
     * @param username
     * @return
     */
    public boolean checkIfUsernameExists(String username){
        try{
            // Getting the information on the username
            JestResult result = new SearchController.CheckIfUserNameExists().execute(username).get();
            // Converting the object
            User returnUser = result.getSourceAsObject(User.class);
            // Here we check if we can fetch the UID, if not, then we get a NullPointerException, proving user does not exist.
            Log.i("Error", returnUser.getUID());
        } catch (NullPointerException e){
            // If a null pointer exception was thrown, the user does not exist, therefore we return false
            return false;
        } catch (Exception e) {
            Log.i("Error", e.getMessage());
            // Couldn't communicate with the server, just tell user to get another username
        }
        return true;
    }
}