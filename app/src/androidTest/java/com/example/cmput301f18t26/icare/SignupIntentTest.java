package com.example.cmput301f18t26.icare;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Controllers.DataController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    /**
     * Variables that will be used to create accounts, etc
     */
    private boolean setupAlreadyRun = false;
    private String existingCP;
    private String existingPatient;
    private String newCP;
    private String newPatient;
    private String singleUseCode;

    /**
     * This will create the values for the variables needed for testing.
     */

    @Before
    public void setUp(){
        // Assigning values
        existingCP = "existingTestCP";
        existingPatient = "existingTestPatient";
        newCP = UUID.randomUUID().toString();
        newPatient = UUID.randomUUID().toString();
    }

    /**
     * This test was created to check if the app will let a user create a patient account with a username that already exists on ES.
     */
    @Test
    public void testSignupUserAlreadyTakenPatient(){
//        // Signing up
        onView(withId(R.id.signup_button)).perform(click());
        // User already exists
        onView(withId(R.id.username_entry)).perform(typeText(existingPatient));
        onView(withId(R.id.phone_entry)).perform(typeText("1234567890"));
        onView(withId(R.id.email_entry)).perform(typeText("wasd@gmail.com"));
        onView(withId(R.id.email_entry)).perform(closeSoftKeyboard());
        onView(withId(R.id.role_patient)).perform(click());
        onView(withId(R.id.signup_button)).perform(click());
        // Now we check to see if the button still exists
        onView(withId(R.id.signup_button))
                .check(matches(withText(containsString("Sign Up"))));
    }

    /**
     * This test was created to check if the app will let a user create a care provider account with a username that already exists on ES.
     */
    @Test
    public void testSignupUserAlreadyTakenCareProvider(){
        // Signing up
        onView(withId(R.id.signup_button)).perform(click());
        // User already exists
        onView(withId(R.id.username_entry)).perform(typeText(existingCP));
        onView(withId(R.id.phone_entry)).perform(typeText("1234567890"));
        onView(withId(R.id.email_entry)).perform(typeText("wasd@gmail.com"));
        onView(withId(R.id.email_entry)).perform(closeSoftKeyboard());
        onView(withId(R.id.role_patient)).perform(click());
        onView(withId(R.id.signup_button)).perform(click());
        // Now we check to see if the button still exists
        onView(withId(R.id.signup_button))
                .check(matches(withText(containsString("Sign Up"))));
    }

    /**
     * This test was created to check if new patinets with unique username can be created
     */
    @Test
    public void testUniquePatientSignUp(){
        // Signing up
        onView(withId(R.id.signup_button)).perform(click());
        // User already exists
        onView(withId(R.id.username_entry)).perform(typeText(newPatient));
        onView(withId(R.id.phone_entry)).perform(typeText("1234567890"));
        onView(withId(R.id.email_entry)).perform(typeText("wasd@gmail.com"));
        onView(withId(R.id.email_entry)).perform(closeSoftKeyboard());
        onView(withId(R.id.role_patient)).perform(click());
        onView(withId(R.id.signup_button)).perform(click());
        // Check to see if new user was created
        onView(withId(R.id.login_button))
                .check(matches(withText(containsString("Log In"))));
    }

    /**
     * This test was created to check if new CareProvider with unique username can be created
     */
    @Test
    public void testUniqueCareProviderSignUp(){
        // Signing up
        onView(withId(R.id.signup_button)).perform(click());
        // User already exists
        onView(withId(R.id.username_entry)).perform(typeText(newCP));
        onView(withId(R.id.phone_entry)).perform(typeText("1234567890"));
        onView(withId(R.id.email_entry)).perform(typeText("wasd@gmail.com"));
        onView(withId(R.id.email_entry)).perform(closeSoftKeyboard());
        onView(withId(R.id.role_patient)).perform(click());
        onView(withId(R.id.signup_button)).perform(click());
        // Check to see if new user was created
        onView(withId(R.id.login_button))
                .check(matches(withText(containsString("Log In"))));
    }
}
