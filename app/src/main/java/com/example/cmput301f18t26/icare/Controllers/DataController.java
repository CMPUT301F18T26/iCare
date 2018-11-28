package com.example.cmput301f18t26.icare.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.CareProviderRecord;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.RecordDeserializer;
import com.example.cmput301f18t26.icare.UserDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;

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
    private BaseRecord selectedRecord = null; //set when a record is selected from a list
    private String currentBodyLocation = null;

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

    /*
     * The following data structures were created to support persistence, offline behavior and login
     * behavior that we were required to implement.
     */

    /**
     * Data structure stores the list of users who have successfully logged in on this device in the
     * past by either creating a new account on this device or by using a single use code to login.
     */
    private List<User> usersThatHaveSuccessfullyLoggedIn = new ArrayList<>();

    /**
     * Data structure to hold images. Its a hash map to make sure that out look time is constant.
     */
    private HashMap<String, ImageAsString> imageAsStringsHash = new HashMap<>();

    /**
     * This data structure stores the records or problems that need to be synced with the server
     * when we regain connectivity.
     */
    private List<Problem> problemsSavedOnlyLocally = new ArrayList<>();
    private List<BaseRecord> recordsSavedOnlyLocally = new ArrayList<>();
    private List<ImageAsString> imagesSavedLocally = new ArrayList<>();
    private boolean checkedInternet = false;
    private boolean haveInternet;

    /**
     * Files names that are used when we read or write data from files in this class
     */
    private String loggedInUserFile = "loggedInUserFile_test";
    private String problemStorageFile = "problem_storage_test";
    private String recordStorageFile = "record_storage_test";
    private String usersThatHaveSuccessfullyLoggedInFile = "usersThatHaveSuccessfullyLoggedInFile_test";
    private String problemsSavedOnlyLocallyFile = "problemsSavedOnlyLocally_file";
    private String baseRecordsSavedOnlyLocallyFile = "baseRecordsSavedOnlyLocallyFile_file";
    private String imagesSavedLocallyFile = "imagesSavedLocallyFile_file";

    /**
     * Private constructor here to enforce Singleton Pattern
     */
    private DataController() {
        checkInternetAfterFiveSecs();
    }

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

    /**
     * Was created so that it would check if we have network connectivity every so often
     */
    private void checkInternetAfterFiveSecs(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkedInternet = false;
                checkInternet();
                checkInternetAfterFiveSecs();
            }
        }, 5000);
    }

    /**
     * Created to check if device is online or offline
     */
    public boolean checkInternet(){
        if (checkedInternet == false) {
            Boolean internetStatus = false;
            try {
                internetStatus = new SearchController.CheckConnection().execute(false).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            haveInternet = internetStatus;
            checkedInternet = true;
        }

        return haveInternet;
    }

    /**
     * Created to send the data changes made offline to server
     */
    public void sendDataToServer(){
        // First send all the problem
        boolean remove = true;
        for (Problem problem: problemsSavedOnlyLocally){
            // If we have a internet connection then save to ElasticSearch as well
            boolean internetStatus =  this.checkInternet();
            if (internetStatus) {
                try {
                    JestResult result = new SearchController.AddProblem().execute(problem).get();
                } catch (Exception e) {
                    Log.i("Error", "Failed to create the problem", e);
                    remove = false;
                }
            }
        }

        if (remove) {
            problemsSavedOnlyLocally = new ArrayList<>();
        }
        remove = true;

        // Now send all the records saved locally
        for (BaseRecord record: recordsSavedOnlyLocally){
            boolean internetStatus =  this.checkInternet();
            if (internetStatus) {
                try {
                    JestResult result = new SearchController.AddRecord().execute(record).get();
                } catch (Exception e) {
                    remove = false;
                    Log.i("Error", "Failed to create the record on ES", e);
                }
            }
        }

        if (remove) {
            recordsSavedOnlyLocally = new ArrayList<>();
        }
        remove = true;

        // Now send images saved locally
        for (ImageAsString ias: imagesSavedLocally){
            boolean internetStatus =  this.checkInternet();
            if (internetStatus) {
                try {
                    JestResult result = new SearchController.AddImage().execute(ias).get();
                } catch (Exception e) {
                    remove = false;
                    Log.i("Error", "Failed to create the record on ES", e);
                }
            }
        }

        if (remove) {
            imagesSavedLocally = new ArrayList<>();
        }
    }
    // ------------------------ USER LOGIN/SIGNUP/LOGGEDINUSER METHODS -----------------------------

    /**
     * Signing up a new user
     * @param user
     */
    public void signup(User user) {
        try {
            // The account was successfully created using this device
            new SearchController.AddUser().execute(user);
            // Now we can add it to a list of user accounts that were created on this device
            User userToAdd = null;
            // Getting the user first
            JestResult result = new SearchController.SignInUser().execute(user.getUsername()).get();
            // ooooo hacks!!!
            User fetchedCurrentUser = result.getSourceAsObject(User.class);
            // check the role and unpack to the proper object so that no data is lost
            if (fetchedCurrentUser.getRole() == 0) {
                userToAdd = result.getSourceAsObject(Patient.class);
            } else {
                userToAdd = result.getSourceAsObject(CareProvider.class);
            }
            // Now storing it to file
            usersThatHaveSuccessfullyLoggedIn.add(userToAdd);
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
            // Even more hacky
            Patient fetchedCurrentUser = result.getSourceAsObject(Patient.class);
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
     * Logging in the last logged in user
     * @return
     */
    public User login(){
        return loggedInUser;
    }

    /**
     * Logging the user out
     */
    public void logout(){
        loggedInUser = null;
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
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
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
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
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
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            try {
                allPatientStorage = new SearchController.SearchPatients().execute(username).get();
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
        /*
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
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            try {
                JestResult result = new SearchController.AddProblem().execute(problem).get();
            } catch (Exception e) {
                Log.i("Error", "Failed to create the problem", e);
            }
        } else{
            problemsSavedOnlyLocally.add(problem);
        }
    }

    /**
     * Takes a user and returns the Problem list mapping to that user in ProblemStorage
     * @param userId
     * @return
     */
    public List<Problem> getProblems(String userId){
        // Check local storage first
        if (problemsSavedOnlyLocally.contains(userId)) {
            return problemStorage.get(userId);
        }
        List<Problem> problemList = null;
        // Checking if we have server connectivity
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            // Connected to server
            try {
                // Getting the results
                JestResult result = new SearchController.GetProblems().execute(userId).get();
                // Putting it into a list
                if (result != null){
                    problemStorage.put(userId, result.getSourceAsObjectList(Problem.class));
                }
                return problemStorage.getOrDefault(userId, new ArrayList<Problem>());
            } catch (Exception e) {
                Log.i("Error", "Failed to fetch problems from the server", e);
                // Got an error, try getting it locally
                return problemStorage.getOrDefault(userId, new ArrayList<Problem>());
            }
        }
        return problemStorage.getOrDefault(userId, new ArrayList<Problem>());
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
        // If we have a internet connection then save to ElasticSearch as well
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            try {
                // We are just calling add problem since it contains teh same logic as updating it
                JestResult result = new SearchController.AddProblem().execute(problem).get();
            } catch (Exception e) {
                Log.i("Error", "Failed to create the problem", e);
                problemsSavedOnlyLocally.add(problem);
            }
        } else{
            problemsSavedOnlyLocally.add(problem);
        }

        // Now we update the info locally
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
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            try {
                // We are just calling add problem since it contains teh same logic as updating it
                JestResult som = new SearchController.DeleteProblem().execute(problem.getUID()).get();
                // Delete associated records
                som = new SearchController.DeleteRecordsAssociatedWithProblem().execute(problem.getUID()).get();
            } catch (Exception e) {
                Log.i("Error", "Failed to create the problem on ES", e);
            }
        }// Offline deleting not supported

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
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            try {
                JestResult result = new SearchController.AddRecord().execute(record).get();
            } catch (Exception e) {
                Log.i("Error", "Failed to create the record on ES", e);
                recordsSavedOnlyLocally.add(record);
            }
        } else{
            recordsSavedOnlyLocally.add(record);
        }
    }

    /**
     * Get all records associated with a problem.
     * @param problem
     * @return
     */
    public List<BaseRecord> getRecords(Problem problem){
        // if the problem is already in the record storage, return the related records.
        // this should be kept up to data.
        if (recordStorage.containsKey(problem.getUID())){
            return recordStorage.get(problem.getUID());
        } else {
            // Otherwise we need to grab records.
            fetchRecords();
            return recordStorage.getOrDefault(problem.getUID(), new ArrayList<BaseRecord>());
        }
    }

    /**
     * Get all records and store them in record storage
     */
    private void fetchRecords() {
        // Checking if we have server connectivity
        Set<String> problemUIDs = problemStorage.keySet();

        boolean connection = this.checkInternet();
        if (connection) {
            for (String pUID : problemUIDs) {
                fetchUserRecords(pUID);
                fetchCareProviderRecords(pUID);
            }
        }
    }

    private void fetchUserRecords(String problemUID){
        try{
            // Get all User records related to this user from the server
            JestResult result = new SearchController.GetAllRecords().execute(problemUID, "0").get();
            // Putting the records into temp storage
            if (result != null) {
                for (UserRecord r : result.getSourceAsObjectList(UserRecord.class)) {
                    if (recordStorage.containsKey(r.getProblemUID())) {
                        recordStorage.get(r.getProblemUID()).add(r);
                    } else {
                        List<BaseRecord> records = new ArrayList<>();
                        records.add(r);
                        recordStorage.put(r.getProblemUID(), records);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Error", "Failed to fetch records from server.");
            // Something went wrong, just give local records back
        }
    }

    private void fetchCareProviderRecords(String problemUID){
        try{
            // Get all User records related to this user from the server
            JestResult result = new SearchController.GetAllRecords().execute(problemUID, "1").get();
            // Putting the records into temp storage
            if (result != null) {
                for (CareProviderRecord r : result.getSourceAsObjectList(CareProviderRecord.class)) {
                    if (recordStorage.containsKey(r.getProblemUID())) {
                        recordStorage.get(r.getProblemUID()).add(r);
                    } else {
                        List<BaseRecord> records = new ArrayList<>();
                        records.add(r);
                        recordStorage.put(r.getProblemUID(), records);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Error", "Failed to fetch records from server.");
            // Something went wrong, just give local records back
        }
    }


    public void setCurrentBodyLocation(String bodyLocation){
        currentBodyLocation = bodyLocation;
    }

    public String getCurrentBodyLocation(){
        return currentBodyLocation;
    }
    /// ------------------------ IMAGE METHODS -----------------------------------------------------


    public void addPhoto(ImageAsString ias){
        // save to our local DataStructure for Problems
        String imageID = ias.getUID();

        // add the record to this list
        imageAsStringsHash.put(imageID, ias);
        // If we have a internet connection then save to ElasticSearch as well
        boolean internetStatus =  this.checkInternet();
        if (internetStatus) {
            try {
                JestResult result = new SearchController.AddImage().execute(imageAsStringsHash.get(imageID)).get();
            } catch (Exception e) {
                Log.i("Error", "Failed to create the record on ES", e);
                imagesSavedLocally.add(ias);
            }
        } else{
            imagesSavedLocally.add(ias);
        }
    }

    public ImageAsString getPhoto(String UID){
        // save to our local DataStructure for Problems
        // Check if UID exists in has, if yes, just return
        ImageAsString ias = imageAsStringsHash.get(UID);
        if (ias != null){
            return ias;
        }else {
            boolean internetStatus =  this.checkInternet();
            if (internetStatus) {
                // Getting the result from the server
                JestResult result = null;
                try {
                    result = new SearchController.GetImage().execute(UID).get();
                } catch (Exception e) {
                    // Getting the record
                    Log.e("Error", "Could not fetch image from server");
                    Log.e("Error", e.getMessage());
                }
                // Saving to hasp map
                ias = result.getSourceAsObject(ImageAsString.class);
                imageAsStringsHash.put(UID, ias);
            }
        }

        return ias;
    }

    public void ClearPhotosHashMap() {
        imageAsStringsHash = new HashMap<>();
    }

    public void setSelectedRecord(BaseRecord record) { this.selectedRecord = record; }


    public BaseRecord getSelectedRecord() { return this.selectedRecord; }

    /// ------------------------ FILE READ/WRITE METHODS -------------------------------------------

    /**
     * The method should be called immediately upon the creation of the MainActivity because we need
     * to read data that was written to files to fetch state of the app.
     * @param context
     */
    public void readDataFromFiles(Context context){
        // First we'll check if there was a loggedInUser
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(loggedInUserFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserDeserializer())
                    .create();
            // Type of user
            Type userType = new TypeToken<User>(){}.getType();
            loggedInUser = gson.fromJson(in, userType);
        } catch (IOException e){
            Log.e("Error", "Could not read last logged in user from file");
        }
        // Commented out to test ES

        // Now we try to read problems from the last run
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(problemStorageFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            Gson gson = new GsonBuilder().create();
            // Now we create the type
            Type mapListProblemType = new TypeToken<Map<String, List<Problem>>>(){}.getType();
            // Now reading from file
            problemStorage = gson.fromJson(in, mapListProblemType);
        } catch (IOException e){
            Log.e("Error", "Could not read problems from last use");
        }

        // Now we try to read records from the last run
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(recordStorageFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            // Custom deserializer for this class
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(BaseRecord.class, new RecordDeserializer())
                    .create();
            // Now we create the type
            Type mapListRecordType = new TypeToken<Map<String, List<UserRecord>>>(){}.getType();
            // Now reading from file
            recordStorage = gson.fromJson(in, mapListRecordType);
        } catch (IOException e){
            Log.e("Error", "Could not read problems from last use");
        }

        // Now we try to read the users that have usersThatHaveSuccessfullyLoggedIn on this device
        // by signing up on this or using a unique code to log in
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(usersThatHaveSuccessfullyLoggedInFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            Gson gson = new GsonBuilder().create();
            // Now we create the type
            Type userListType = new TypeToken<List<User>>(){}.getType();
            // Now reading from file
            usersThatHaveSuccessfullyLoggedIn = gson.fromJson(in, userListType);
        } catch (IOException e){
            Log.e("Error", "Could not read usersThatHaveSuccessfullyLoggedIn from last use");
        }

        // Now we try to read problems that were only saved locally and were not synced with the cloud
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(problemsSavedOnlyLocallyFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            Gson gson = new GsonBuilder().create();
            // Now we create the type
            Type problemList = new TypeToken<List<Problem>>(){}.getType();
            // Now reading from file
            problemsSavedOnlyLocally = gson.fromJson(in, problemList);
        } catch (IOException e){
            Log.e("Error", "Could not read problemsSavedOnlyLocallyFile from last use");
        }

        // Now we try to read base records that were only saved locally and were not synced with the cloud
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(baseRecordsSavedOnlyLocallyFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            Gson gson = new GsonBuilder().create();
            // Now we create the type
            Type baseRecordList = new TypeToken<List<UserRecord>>(){}.getType();
            // Now reading from file
            recordsSavedOnlyLocally = gson.fromJson(in, baseRecordList);
        } catch (IOException e){
            Log.e("Error", "Could not read baseRecordsSavedOnlyLocally from last use");
        }

        // Now we try to read base records that were only saved locally and were not synced with the cloud
        try {
            // Getting to read the file
            FileInputStream fis = context.openFileInput(imagesSavedLocallyFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            // Getting the gson builder
            Gson gson = new GsonBuilder().create();
            // Now we create the type
            Type baseRecordList = new TypeToken<List<ImageAsString>>(){}.getType();
            // Now reading from file
            imagesSavedLocally = gson.fromJson(in, baseRecordList);
        } catch (IOException e){
            Log.e("Error", "Could not read imagesSavedLocally from last use");
        }
    }

    /**
     * The method write all the data in memory to files on the disk for persistence.
     * @param context
     */
    public void writeDataToFiles(Context context){
        // First we'll check if there was a loggedInUser
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(loggedInUserFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(loggedInUser, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write last logged in user from file");
        }

        // Now we try to read problems from the last run
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(problemStorageFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(problemStorage, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write problems from last use");
        }

        // Now we try to read records from the last run
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(recordStorageFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(recordStorage, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write problems from last use");
        }

        // Now we try to read the users that have usersThatHaveSuccessfullyLoggedIn on this device
        // by signing up on this or using a unique code to log in
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(usersThatHaveSuccessfullyLoggedInFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(usersThatHaveSuccessfullyLoggedIn, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write usersThatHaveSuccessfullyLoggedIn from last use");
        }

        // Now we try to read problems that were only saved locally and were not synced with the cloud
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(problemsSavedOnlyLocallyFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(problemsSavedOnlyLocally, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write problemsSavedOnlyLocallyFile from last use");
        }

        // Now we try to read base records that were only saved locally and were not synced with the cloud
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(baseRecordsSavedOnlyLocallyFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(recordsSavedOnlyLocally, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write baseRecordsSavedOnlyLocally from last use");
        }

        // Now we try to read base records that were only saved locally and were not synced with the cloud
        try {
            // Stream to send data to file
            FileOutputStream fos = context.openFileOutput(imagesSavedLocallyFile, Context.MODE_PRIVATE);
            // Getting the write which will be used to write to file
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            // Writing JSON
            Gson gson = new Gson();
            gson.toJson(imagesSavedLocally, out);
            out.flush();
            // Closing File
            fos.close();
        } catch (IOException e){
            Log.e("Error", "Could not write baseRecordsSavedOnlyLocally from last use");
        }
    }

    /// ---------------------------------Search Records/Problems methods----------------------------
    ArrayList<Problem> pSearchResults;
    ArrayList<BaseRecord> rSearchResults;
    /**
     * Search by Keyword
     *
     * Return all Problems or Records that contain the provided keyword
     * Currently implemented using a simple bubble search :(
     */
    public void searchByKeyword(Class c, String keyword){
        for (Problem p : getProblems(getCurrentUser().getUID())) {
            if (p.getDescription().contains(keyword) || p.getTitle().contains(keyword)) {
                pSearchResults.add(p);
            }
        }
        for (String pUID : recordStorage.keySet()){
            for (BaseRecord r : Objects.requireNonNull(recordStorage.get(pUID))) {
                if (r.getComment().contains(keyword) || r.getTitle().contains(keyword)) {
                    rSearchResults.add(r);
                }
            }
        }
    }

    public List<BaseRecord> getRecordSearchResults() {
        return rSearchResults;
    }

    public List<Problem> getProblemSearchResults() {
        return pSearchResults;
    }

}