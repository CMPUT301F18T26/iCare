package com.example.cmput301f18t26.icare;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.LoginActivity;
import com.example.cmput301f18t26.icare.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

/*
 ***************************************************************************************************
 * THE INTENT TESTS NEED TO RUN IN A SPECIFIC SEQUENCE TO TEST THE UI PROPERLY, READ THE "How to run
 * tests" SECTION IN THE README FILE TO LEARN HOW THIS SEQUENCE.
 ***************************************************************************************************
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    private String existingCP;
    private String existingPatient;
    private String singleUseCode;

    /**
     * This will create the values for the variables needed for testing.
     */

    @Before
    public void setUp(){
        // Assigning values
        existingCP = "existingTestCP";
        existingPatient = "existingTestPatient";
    }

    /**
     * Test with right login information with patient
     */
    @Test
    public void testLoginPatient(){
        try{
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText(containsString("Log out"))).perform(click());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                ;
            }
        } catch (Exception e){

        }
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText(existingPatient));
        onView(withId(R.id.login_button)).perform(click());
        // Should have gone through, Problems label should be there.
        onView(withId(R.id.patient_conditions_label))
                .check(matches(withText(containsString("Problems"))));
    }

    /**
     * Test with right login information with care provider
     */
    @Test
    public void testLoginCareProvider(){
        try{
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText(containsString("Log out"))).perform(click());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                ;
            }
        } catch (Exception e){

        }
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText(existingCP));
        onView(withId(R.id.login_button)).perform(click());
        // Should have gone through, Problems label should be there.
        onView(withId(R.id.care_provider_patient_list_title))
                .check(matches(withText(containsString("Patients"))));
    }

    /**
     * Test the wrong single use code to login
     */
    @Test
    public void testSingleUseCodeLoginWrong(){
        try{
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText(containsString("Log out"))).perform(click());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                ;
            }
        } catch (Exception e){

        }
        // Put a random code in
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.code_entry)).perform(typeText(UUID.randomUUID().toString()));
        onView(withId(R.id.login_button)).perform(click());
        // Should not have gone through
        onView(withId(R.id.login_button))
                .check(matches(withText(containsString("Log In"))));
    }

    /**
     * Test a random username, should not log in
     */
    @Test
    public void testLoginUncreatedUserName(){
        try{
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText(containsString("Log out"))).perform(click());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                ;
            }
        } catch (Exception e){

        }
        // Put a random code in
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText(UUID.randomUUID().toString()));
        onView(withId(R.id.login_button)).perform(click());
        // Should not have gone through
        onView(withId(R.id.login_button))
                .check(matches(withText(containsString("Log In"))));
    }

}
