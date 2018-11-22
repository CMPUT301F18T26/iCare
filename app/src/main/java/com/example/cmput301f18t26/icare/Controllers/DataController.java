package com.example.cmput301f18t26.icare.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestResult;

import static android.support.v4.content.ContextCompat.getSystemService;

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

    // ------------------------ DO NOT TOUCH THE FOLLOWING -----------------------------------------

    /**
     * Private fields that represent certain application states such as current logged in User,
     * our lone DataController singleton, and DataStructures for caching ElasticSearch data
     */
    private static DataController onlyInstance = null; // the lone instance of our DataController
    private User loggedInUser = null; // set when a user logs in
    private Problem selectedProblem = null; // set when a problem is selected from a list

    /**
     * Data structure for storing any Patients seen associated with a Care Provider
     * Key = User.UID : Val = ArrayList<User>
     */
    private List<Patient> patientStorage = new ArrayList<>();

    /**
     * Data structure for ALL patients, don't have to be associated with a Care Provider
     * This is meant for SearchAddPatientsActivity
     * Key = User.UID : Val = ArrayList<User>
     */
    private List<Patient> allPatientStorage = new ArrayList<>();

    /**
     * Data structure for storing any Problems seen (Problems associated with a User)
     * We use a map here with user ID's as the key to grab Problems in constant runtime
     * Key = User.UID : Val = ArrayList<Problem>
     */
    private Map<String, List<Problem>> problemStorage = new HashMap<>();

    /**
     * Data structure for storing any Records seen (Records associated with a Problem)
     * We use a map here with problem ID's as the key to grab Records in constant runtime
     * Key = Problem.UID : Val = ArrayList<Record>
     */
    private Map<String, List<BaseRecord>> recordStorage = new HashMap<>();

    /**
     * Private constructor here to enforce Singleton Pattern
     */
    private DataController() { }

    // ------------------------ PUBLIC METHODS FOR INTERACTING WITH DATACONTROLLER -----------------

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

    // ------------------------ USER LOGIN/SIGNUP/LOGGEDINUSER METHODS -----------------------------

    /**
     * Signing up a new user
     * @param user
     */
    public void signup(User user) {
        try {
            new SearchController.AddUser().execute(user);
        } catch (Exception e) {
            Log.i("Error", "Failed to create the user", e);
        }
    }

    /**
     * Logging in a new user
     * @param username
     * @return User
     */
    public User login(String username){
        try {
            JestResult result = new SearchController.SignInUser().execute(username).get();
            // ooooo hacks!!!
            User fetchedCurrentUser = result.getSourceAsObject(User.class);
            // check the role and unpack to the proper object so that no data is lost
            if (fetchedCurrentUser.getRole() == 0) {
                loggedInUser = result.getSourceAsObject(Patient.class);
            } else {
                loggedInUser = result.getSourceAsObject(CareProvider.class);
            }
            return loggedInUser;
        } catch (Exception e) {
            loggedInUser = null;
            Log.i("Error", "Problem talking to ES instance");
        }
        return loggedInUser;
    }

    /**
     * Retrives the current logged in User (can only be set via login)
     * @return current user
     */
    public User getCurrentUser() {
        return loggedInUser;
    }

    // ------------------------ CARE PROVIDER's PATIENT METHODS ------------------------------------

    /**
     * Adds a patient to the list of patients maintained by this class.
     * @param patient
     */
    public void addPatient(Patient patient){
        patientStorage.add(patient); // save to our local patient DataStructure
        // If we have a internet connection then save the patient to ElasticSearch as well
        if (MainActivity.checkConnection()) {
            patient.updateUserInfo(); // this saves the user's new CareProviderUID to ES
            // We do not need to do anything for care provider if the person is offline
        }
    }

    /**
     * Fetch entire patient list for loggedInUser, assuming loggedInUser is a Care Provider
     * we could add error checking here and return empty list if it is not but i didn't because
     * then we'd need to add it for all overloaded methods. JUST BE CAREFUL.
     *
     * @return List<Patient>
     */
    public List<Patient> getPatients() {
        // If we have a internet connection then fetch from ElasticSearch first
        if (MainActivity.checkConnection()) {
            try {
                patientStorage = new SearchController.GetPatients().execute(loggedInUser.getUID()).get();
            } catch (Exception e) {
                Log.i("Error", "Could not get the list of patients associated to this care provider");
            }
        }
        return patientStorage;
    }

    /**
     * Searches for patients by username, like getPatients but username is a search param.
     *
     * This makes use of java's method overloading to simulate optional params.
     *
     * We'll perform a manual search here so that we can make use of offline data and perform
     * unit testing
     * @param username
     * @return
     */
    public List<Patient> getPatients(String username){
        List<Patient> result = new ArrayList<>();
        // If we have a internet connection then fetch from ElasticSearch first
        if (MainActivity.checkConnection()) {
            try {
                allPatientStorage = new SearchController.SearchPatients().execute().get();
            } catch (Exception e) {
                Log.i("Error", "Could not get the list of patients associated to this care provider");
            }
        }
        for (Patient patient : allPatientStorage) {
            if (patient.getUsername().contains(username)) {
                result.add(patient);
            }
        }
        return result;
    }

    /// ------------------------ PROBLEM METHODS ---------------------------------------------------

    /**
     * Adds a problem to a DataStructure maintained by this class that maps lists of problems
     * to the UID of the user they belong to
     *
     * This provides fast lookup times when getting problems associated with a certain user
     *
     * @param problem
     */
    public void addProblem(Problem problem) {
        // save to our local DataStructure for Problems
        String userUID = problem.getUserUID();
        /**
         * This is literally straight outta a leetcode question ;)
         * try to fetch the current ArrayList of problems associated with the user, if user doesn't
         * exist in problemStorage yet (no problems) then load a default blank rrayList<Problem>.
         */
        List<Problem> problemList = problemStorage.getOrDefault(userUID, new ArrayList<Problem>());
        // add the problem to this list
        problemList.add(problem);
        // put the list back into problemStorage
        problemStorage.put(userUID, problemList);
        // If we have a internet connection then save to ElasticSearch as well
        if (MainActivity.checkConnection()) {
            try {
                JsonObject jsonProblem = new SearchController.AddProblem().execute(problem).get()
                        .getJsonObject();
                String problemPID = jsonProblem.get("_id").toString();
                Log.i("Created", problemPID);
            } catch (Exception e) {
                Log.i("Error", "Failed to create the problem", e);
            }
            /**
             * We will need to think of a clever way to save the problem next time we have
             * access to internet if it is not saved at this point.
             *
             * We could:
             * Add a flag to problem that indicates whether it was saved to ElasticSearch
             * Attempt to save all problems that do not have this flag set every time addProblem
             * is called.
             */
        }
    }

    /**
     * Takes a user and returns the Problem list mapping to that user in ProblemStorage
     * @param user
     * @return
     */
    public List<Problem> getProblems(User user){
        String userUID = user.getUID();
        return problemStorage.getOrDefault(userUID, new ArrayList<Problem>());
    }

    /**
     * Updates a problem from the problemList
     * @param problem
     */
    public void updateProblem(Problem problem) {
        /*
         * this is easy, just remove the problem by finding the problem with the same UID,
         * then add it back, if you do enough leetcode you will know this pattern well lol
         */
        String userUID = problem.getUserUID();
        List<Problem> problemsList = problemStorage.get(userUID);
        for (Problem oldProblem : problemsList) {
            if (oldProblem.getUID().equals(problem.getUID())) {
                problemsList.remove(oldProblem);
            }
        }
        addProblem(problem);
        // Need to set the reference again
        selectedProblem = problem;
    }

    /**
     * Deletes the problem from the problemList
     * @param problem
     */
    public void deleteProblem(Problem problem) {
        // Getting the user id of the problem
        String userUID = problem.getUserUID();
        // Now removing the problem from the list
        problemStorage.get(userUID).remove(problem);
        // We also need to remove the records for this problem
        if (recordStorage.containsKey(problem.getUID())){
            recordStorage.remove(problem.getUID());
        }
    }

    /**
     * Sets the passed in problemID to the to the currentProblem id.
     * @param problem
     */
    public void setSelectedProblem(Problem problem) {
        this.selectedProblem = problem;
    }

    /**
     * Returns the currentProblem saved in this class.
     * @return
     */
    public Problem getSelectedProblem() {
        return this.selectedProblem;
    }

    /// ------------------------ RECORD METHODS ----------------------------------------------------

    /**
     * Add record to the list of records maintained by this class.
     * @param record
     * @return
     */
    public void addRecord(BaseRecord record){
        // save to our local DataStructure for Problems
        String problemUID = record.getProblemUID();
        /**
         * Same pattern as Problem
         */
        List<BaseRecord> recordList = recordStorage.getOrDefault(problemUID, new ArrayList<BaseRecord>());
        // add the record to this list
        recordList.add(record);
        // put the list back into problemStorage
        recordStorage.put(problemUID, recordList);
        // If we have a internet connection then save to ElasticSearch as well
        if (MainActivity.checkConnection()) {
            /**
             * TO BE IMPLEMENTED:
             * SOMEONE WILL NEED TO ADD RECORDS TO ELASTICSEARCH
             *
             * We will need to think of a clever way to save the problem next time we have
             * access to internet if it is not saved at this point.
             *
             * We could:
             * Add a flag to problem that indicates whether it was saved to ElasticSearch
             * Attempt to save all problems that do not have this flag set every time addProblem
             * is called.
             */
        }
    }

    /**
     * Get all records associated with a problem.
     * @param problem
     * @return
     */
    public List<BaseRecord> getRecords(Problem problem){
        String problemUID = problem.getUID();
        return recordStorage.getOrDefault(problemUID, new ArrayList<BaseRecord>());
    }
}