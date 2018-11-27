package com.example.cmput301f18t26.icare.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * SearchController is used for interacting with our ElasticSearch instance. It can perform queries
 * and save data.
 *
 * This class maintains a lone static instance of a Jest client - an Android HTTP Rest client for
 * ElasticSearch.
 */
public class SearchController {

    private static JestDroidClient jestClient;

    private static final String elasticSearchURL = "http://cmput301.softwareprocess.es:8080/";
    private static final String groupIndex = "cmput301f18t26test";
    private static final String checkType = "check";
    private static final String userType = "user";
    private static final String problemType = "problem";
    private static final String recordType = "record";
    private static final String imageType = "image";
    private static final String singleUseCodeType = "singleUseCode";

    /**
     * This method is used to instantiate our jestClient and save the instance to the
     * SearchController class.
     *
     * It is called from our DataController instance when it is first instantiated.
     */
    public static void setup() {
        DroidClientConfig clientConfig = new DroidClientConfig.Builder(elasticSearchURL).build();

        JestClientFactory jestClientFactory = new JestClientFactory();
        jestClientFactory.setDroidClientConfig(clientConfig);
        jestClient = (JestDroidClient) jestClientFactory.getObject();
    }

    /**
     * This class checks to see if our app is connected to the internet for making decisions
     * about whether to use SearchController/ElasticSearch or to use the local cache in
     * DataController
     *
     * True = online
     * https://stackoverflow.com/questions/1402005/how-to-check-if-internet-connection-is-present-in-java
     */
    public static class CheckConnection extends AsyncTask<Boolean, Void, Boolean> {
        private JestResult result;
        @Override
        protected Boolean doInBackground(Boolean... none) {
            Index index = new Index.Builder("1").index(groupIndex).type(checkType).refresh(true).id("1").build();
            Log.i("Info", index.toString());
            try {
                result = jestClient.execute(index);
                return true;
            } catch (Exception e) {
                Log.i("Error", "Jest failed to execute", e);
                return false;
            }
        }
    }

    /**
     * Class adds a user to ES.
     */
    public static class AddUser extends AsyncTask<User, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(User... users) {
            User user = users[0];
            Index index = new Index.Builder(user).index(groupIndex).type(userType).refresh(true).id(user.getUID()).build();
            Log.i("Error", index.toString());
            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.i("Error", "Jest failed to execute", e);
                return null;
            }
        }
    }

    public static class CheckIfUserNameExists extends AsyncTask<String, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground (String... userParams) {

            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"username\": \"" + userParams[0] + "\" } }] } } }";

            Search search = new Search.Builder(query).addIndex(groupIndex).addType(userType).build();

            try {
                result = jestClient.execute(search);
                return result;
            } catch (Exception e) {
                Log.e("Error", "Problem communicating with the ElasticSearch Instance");
                return null;
            }
        }
    }

    public static class AddProblem extends AsyncTask<Problem, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(Problem... problems) {
            Problem problem = problems[0];
            Index index = new Index.Builder(problem).index(groupIndex).type(problemType).refresh(true)
                    .id(problem.getUID()).build();
            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.i("Error", "Jest failed to execute", e);
                return null;
            }
        }
    }


    public static class SignInUser extends AsyncTask<String, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground (String... userParams) {
            String query = null;
            if (userParams[0].length() >= 8){
                query =
                        "{\n" +
                                "  \"query\": {\n" +
                                "    \"bool\": {\n" +
                                "      \"must\": [\n" +
                                "        { \"match\": { \"username\": \"" + userParams[0] + "\"}}\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  }\n" +
                                "}";
            } else {
                query =
                        "{\n" +
                                "  \"query\": {\n" +
                                "    \"bool\": {\n" +
                                "      \"must\": [\n" +
                                "        { \"match\": { \"singleUseCode\": \"" + userParams[0] + "\"}}\n" +
                                "      ]\n" +
                                "    }\n" +
                                "  }\n" +
                                "}";
            }

            Search search = new Search.Builder(query).addIndex(groupIndex).addType(userType).build();

            try {
                result = jestClient.execute(search);
                return result;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                return null;
            }
        }
    }

    /**
     * Class was created to fetch a user for a given UID
     */
    public static class GetUserInfoUsingUId extends AsyncTask<String, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(String... userId) {
            // Creating the query for the user
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"_id\": \"" + userId[0] + "\" } }] } } }";

            Search search = new Search.Builder(query).addIndex(groupIndex).addType(userType).build();
            try {
                result = jestClient.execute(search);
                return result;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                return null;
            }
        }
    }

    /**
     * Method updates an existing user on Elasticsearch
     */
    public static class UpdateInformationForUser extends AsyncTask<User, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(User... users) {
            // Creating the query to update the user
            User user = users[0];
            Index index = new Index.Builder(user).index(groupIndex).type(userType).refresh(true).id(user.getUID()).build();

            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                return null;
            }
        }
    }

    public static class GetPatients extends AsyncTask<String, Void, ArrayList<Patient>> {

        @Override
        protected ArrayList<Patient> doInBackground(String... careProviderUID) {

            ArrayList<Patient> patients = new ArrayList<>();

            String query =
                    "{\n" +
                    "  \"query\": {\n" +
                    "    \"bool\": {\n" +
                    "      \"must\": [\n" +
                    "        { \"match\": { \"careProviderUID\": \"" + careProviderUID[0] + "\"}}\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex(groupIndex).addType(userType).build();

            try {
                SearchResult result = jestClient.execute(search);
                if (result.isSucceeded()){
                    List<Patient> patientList = result.getSourceAsObjectList(Patient.class);
                    patients.addAll(patientList);
                } else {
                    Log.e("Error", "Could not get the patient list for this care provider");
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return patients;
        }
    }

    public static class SearchPatients extends AsyncTask<String, Void, ArrayList<Patient>> {

        @Override
        protected ArrayList<Patient> doInBackground(String... params) {

            ArrayList<Patient> patients = new ArrayList<>();

            /**
            Get all patients (role == 0) that are not already assigned a care provider
             (careProviderUID == "")
             */
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"username\": \"" + params[0] + "\" } }, { \"match\": { \"role\": \"" + String.valueOf(0) + "\" } }] } } }";

            Search search = new Search.Builder(query).addIndex(groupIndex).addType(userType).build();

            try {
                SearchResult result = jestClient.execute(search);
                if (result.isSucceeded()) {
                    List<Patient> patientList = result.getSourceAsObjectList(Patient.class);
                    patients.addAll(patientList);
                } else {
                    Log.e("Error", "Could not get the patient list for this care provider");
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return patients;
        }
    }

    public static class GetProblems extends AsyncTask<String, Void, JestResult> {

        @Override
        protected JestResult doInBackground(String... params) {
            // Getting the query ready to fetch the problems of a user
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"userUID\": \"" + params[0] + "\" } }] } } }";

            // Getting the search ready
            Search search = new Search.Builder(query).addIndex(groupIndex).addType(problemType).build();

            // Executing the search
            try {
                SearchResult result = jestClient.execute(search);
                if (result.isSucceeded()) {
                    return result;
                } else {
                    Log.e("Error", "Could not get the patient list for this care provider");
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return null;
        }
    }

    public static class DeleteProblem extends AsyncTask<String, Void, JestResult> {
        @Override
        protected JestResult doInBackground(String... params) {
            // Getting the delete query ready
            Delete delete = new Delete.Builder(params[0]).index(groupIndex).type(problemType).build();

            // Executing the query
            try {
                JestResult result = jestClient.execute(delete);
                if (result.isSucceeded()) {
                    return result;
                } else {
                    Log.e("Error", "Could not delete the problem on the server.");
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return null;
        }
    }

    public static class AddRecord extends AsyncTask<BaseRecord, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(BaseRecord... records) {
            BaseRecord record = records[0];
            Index index = new Index.Builder(record).index(groupIndex).type(recordType).refresh(true)
                    .id(record.getUID()).build();
            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.i("Error", "Jest failed to execute", e);
                return null;
            }
        }
    }

    public static class AddImage extends AsyncTask<ImageAsString, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(ImageAsString... image) {
            Index index = new Index.Builder(image[0]).index(groupIndex).type(imageType).refresh(true)
                    .id(image[0].getUID()).build();
            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.i("Error", "Jest failed to execute, image was not sent to server", e);
                return null;
            }
        }
    }

    public static class GetImage extends AsyncTask<String, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(String... imageID) {
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"UID\": \"" + imageID[0] + "\" } }] } } }";

            // Getting the search ready
            Search search = new Search.Builder(query).addIndex(groupIndex).addType(imageType).build();

            try {
                result = jestClient.execute(search);
                return result;
            } catch (Exception e) {
                Log.i("Error", "Jest failed to execute, image was not sent to server", e);
                return null;
            }
        }
    }

    public static class GetRecords extends AsyncTask<String, Void, JestResult> {

        @Override
        protected JestResult doInBackground(String... params) {
            // Getting the query ready to fetch the problems of a user
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"problemUID\": \"" + params[0] + "\" } }] } } }";

            // Getting the search ready
            Search search = new Search.Builder(query).addIndex(groupIndex).addType(recordType).build();

            // Executing the search
            try {
                SearchResult result = jestClient.execute(search);
                if (result.isSucceeded()) {
                    return result;
                } else {
                    Log.e("Error", "Could not get the patient list for this care provider");
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return null;
        }
    }

    public static class GetRecordUsingUID extends AsyncTask<String, Void, JestResult> {

        @Override
        protected JestResult doInBackground(String... params) {
            // Getting the query ready to fetch the problems of a user
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"_id\": \"" + params[0] + "\" } }] } } }";

            // Getting the search ready
            Search search = new Search.Builder(query).addIndex(groupIndex).addType(recordType).build();

            // Executing the search
            try {
                SearchResult result = jestClient.execute(search);
                if (result.isSucceeded()) {
                    return result;
                } else {
                    Log.e("Error", "Could not get the patient list for this care provider");
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return null;
        }
    }

    public static class DeleteRecordsAssociatedWithProblem extends AsyncTask<String, Void, JestResult> {
        @Override
        protected JestResult doInBackground(String... params) {
            // First we need to fetch all the records for this problem
            String query = "{ \"query\": { \"bool\": { \"must\": [{ \"match\": { \"problemUID\": \"" + params[0] + "\" } }] } } }";

            // Getting the search ready
            Search search = new Search.Builder(query).addIndex(groupIndex).addType(recordType).build();

            try {
                JestResult result = jestClient.execute(search);
                // Now we get the records a list
                List<UserRecord> records = result.getSourceAsObjectList(UserRecord.class);
                // For we start a loop for each record found
                for (BaseRecord record: records){
                    // Getting the delete query ready
                    Delete delete = new Delete.Builder(record.getUID()).index(groupIndex).type(recordType).build();
                    // Executing the query
                    result = jestClient.execute(delete);
                    if (!result.isSucceeded()) {
                        Log.e("Error", "Could not delete the problem on the server.");
                    }
                }

            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

            return null;
        }
    }
}
