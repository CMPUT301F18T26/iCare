package com.example.cmput301f18t26.icare;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.Patient;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class CareProviderIntentTest {

    private DataController dataController;

    private static String testPatientUsername = "testP";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void testCareProviderLogin(){
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("testCP"));
        onView(withId(R.id.password_entry)).perform(typeText("123"));
        onView(withId(R.id.login_button)).perform(click());
        // get the dataController
        dataController = DataController.getInstance();
    }

    @Test
    public void testCareProviderAddPatient(){
        // navigate to add patient screen
        onView(withId(R.id.add_new_patient)).perform(click());

        // search for the test patient and add to this care provider
        onView(withId(R.id.patient_search)).perform(typeText(testPatientUsername));
        onView(withId(R.id.search_patients_button)).perform(click());
        // Need it to sleep cause of network delay
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            ;
        }
        onData(anything()).inAdapterView(withId(R.id.care_provider_patient_list_view))
                .atPosition(0).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        // make sure that the patient that was just added AND that patient also has the proper
        // careProvider UID. Do it the old fashion way
        boolean result = false;
        for (Patient patient: dataController.getPatients()) {
            if (patient.getUsername().equals(testPatientUsername) &&
                    patient.getCareProviderUID().equals(dataController.getCurrentUser().getUID())){
                result = true;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testCareProviderViewPatient(){
        // grab the username of the user that we are going to try grabbing
        String pUsername = onData(anything()).inAdapterView(withId(R.id.care_provider_patient_list))
                .atPosition(0).toString();

        // navigate to view patient screen
        onData(anything()).inAdapterView(withId(R.id.care_provider_patient_list))
                .atPosition(0).perform(click());

        // check to make sure that the proper username is being displayed (ie. the proper user
        // is being viewed
        onView(withId(R.id.condition_view_patient_name))
                .check(matches(withText(testPatientUsername)));

        // when problems are added to ES, additional testing can be completed
    }

    @AfterClass
    public static void removePatient() {
        DataController dataController = DataController.getInstance();
        for (Patient p : dataController.getPatients()){
            if (p.getUsername().equals(testPatientUsername)) {
                p.setCareProviderUID("");
                dataController.updateElasticSearchForNewUserInfo(p);
            }
        }
    }
}
