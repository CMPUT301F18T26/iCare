package com.example.cmput301f18t26.icare;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.cmput301f18t26.icare.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.anything;

/*
 ***************************************************************************************************
 * THE INTENT TESTS NEED TO RUN IN A SPECIFIC SEQUENCE TO TEST THE UI PROPERLY, READ THE "How to run
 * tests" SECTION IN THE README FILE TO LEARN HOW THIS SEQUENCE.
 ***************************************************************************************************
 */

public class RecordIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    /**
     * Variables that will be used to log in , etc
     */
    private String existingPatient;

    @Before
    public void setUp(){
        // Assigning values
        existingPatient = "existingTestPatient";
    }

    @Test
    public void testAddViewRecord(){
        //Logging in
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

        //Go to add new problem
        onView(withId(R.id.add_new_problem_button)).perform(click());
        // Now we add values
        String problem = "Flu";
        String description = "test";
        // Place these values into text view
        onView(withId(R.id.condition_name)).perform(replaceText(problem));
        onView(withId(R.id.description)).perform(replaceText(description));
        //Save values
        onView(withId(R.id.save_problem)).perform(click());

        //Go to view problem
        //onView(withId(R.id.patient_conditions_list_view)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.patient_conditions_list_view)).atPosition(0).perform(click());

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            ;
        }

        //Click on addRecord
        onView(withId(R.id.add_new_record_button)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            ;
        }

        // Name and comment
        onView(withId(R.id.view_record_title)).perform(typeText("fake"));
        onView(withId(R.id.record_comment)).perform(typeText("123"));
        onView(withId(R.id.record_comment)).perform(closeSoftKeyboard());

        // Body location
        onView(withId(R.id.body)).perform(click());

        // Assign body location
        onView(withId(R.id.front_chest_button)).perform(click());
        // Now check that the label changed
        onView(withId(R.id.patient_record_body_label_location)).check(matches(withText(BodyLocation.FRONT_CHEST.toString())));

        // Assign new body location
        onView(withId(R.id.back_head_button)).perform(click());
        // Now check that the label changed
        onView(withId(R.id.patient_record_body_label_location)).check(matches(withText(BodyLocation.BACK_HEAD.toString())));

        // Assign new body location
        onView(withId(R.id.front_right_arm_button)).perform(click());
        // Now check that the label changed
        onView(withId(R.id.patient_record_body_label_location)).check(matches(withText(BodyLocation.FRONT_RIGHT_ARM.toString())));

        // Assign new body location
        onView(withId(R.id.back_left_leg_button)).perform(click());
        // Now check that the label changed
        onView(withId(R.id.patient_record_body_label_location)).check(matches(withText(BodyLocation.BACK_LEFT_LEG.toString())));


        // Go to the other one
        onView(withId(R.id.geo)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            ;
        }
        // Add a location
        onView(withId(R.id.saveLocation)).perform(click());

        // Go back to main
        onView(withId(R.id.info)).perform(click());
        // Now save
        onView(withId(R.id.userRecord_save_button)).perform(click());

        // Now view the record
        onData(anything()).inAdapterView(withId(R.id.record_list_view)).atPosition(0).perform(click());

        // Check to see same is same
        onView(withId(R.id.view_record_title))
                .check(matches(withText("fake")));

        // If matches, go back and delete problem
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            ;
        }

        onData(anything()).inAdapterView(withId(R.id.patient_conditions_list_view)).atPosition(0).perform(longClick());
        onView(withText("Yes")).perform(click());
    }
}
