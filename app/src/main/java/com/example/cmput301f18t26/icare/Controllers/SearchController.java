package com.example.cmput301f18t26.icare.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
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

    private static final String elasticSearchURL = "https://641vybd6aa:p5aozx6i80@tonyqian-6515826673.us-west-2.bonsaisearch.net";
    private static final String groupIndex = "icare";
    private static final String userType = "user";

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
     * Class adds a user to ES.
     */
    public static class AddUser extends AsyncTask<User, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(User... users) {
            User user = users[0];
            Index index = new Index.Builder(user).index(groupIndex).type(userType).refresh(true)
                    .id(user.getUID()).build();
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
            Index index = new Index.Builder(problem).index(groupIndex).type("problem").refresh(true)
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

            String query =
                    "{\n" +
                    "  \"query\": {\n" +
                    "    \"bool\": {\n" +
                    "      \"must\": [\n" +
                    "        { \"match\": { \"username\": \"" + userParams[0] + "\"}}\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

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
                Log.e("Error", "Problem communicating with the ElasticSearch Instance");
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
            Index index = new Index.Builder(user).index(groupIndex).type(userType).refresh(true)
                    .id(user.getUID()).build();

            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.e("Error", "Problem communicating with the ElasticSearch Instance");
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
                Log.i("Error", "Something went wrong communicating with the ElasticSearch server");
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
                Log.i("Error", "Something went wrong communicating with the ElasticSearch server");
            }

            return patients;
        }
    }
}
