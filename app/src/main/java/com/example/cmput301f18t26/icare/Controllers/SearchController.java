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

    private static final String elasticSearchURL = "https://641vybd6aa:p5aozx6i80@tonyqian-6515826673.us-west-2.bonsaisearch.net";
    private static final String groupIndex = "icare";

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
            User user = users[0];
            Index index = new Index.Builder(user).index(groupIndex).type("user").refresh(true)
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
}
