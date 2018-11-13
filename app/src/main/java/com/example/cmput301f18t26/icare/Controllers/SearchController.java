package com.example.cmput301f18t26.icare.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cmput301f18t26.icare.Models.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.Index;

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
    private static final String groupIndex = "cmput301f18t26";

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

    public static class AddUser extends AsyncTask<User, Void, JestResult> {
        private JestResult result;

        @Override
        protected JestResult doInBackground(User... users) {
            Index index = new Index.Builder(users[0]).index(groupIndex).type("user").build();
            try {
                result = jestClient.execute(index);
                return result;
            } catch (Exception e) {
                Log.i("Error", "Failed to create the user", e);
                return null;
            }
        }
    }
}
