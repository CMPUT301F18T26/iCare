package com.example.cmput301f18t26.icare;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewProblemIntentTest {
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
    public void testAddProblem(){
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
        //onData(withItemContent("item: 0")).perform(click());
        //Check if displayed problem matches inputted problem
        onView(withId(R.id.condition_view_name))
                .check(matches(withText("Flu")));
        onView(withId(R.id.condition_view_description))
                .check(matches(withText("test")));
    }

    @Test
    public void testEditProblem(){
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

        //Go to view problem
        onData(anything()).inAdapterView(withId(R.id.patient_conditions_list_view)).atPosition(0).perform(click());

        //Go to edit problem
        onView(withId(R.id.edit_condition)).perform(click());
        // Place these new values into text view
        String newProblem = "Headache";
        String newDescription = "test2";
        onView(withId(R.id.condition_name)).perform(replaceText(newProblem));
        onView(withId(R.id.description)).perform(replaceText(newDescription));
        //Save values
        onView(withId(R.id.save_problem)).perform(click());

        //Check if problem matches inputted problem
        onView(withId(R.id.condition_view_name))
                .check(matches(withText("Headache")));
        onView(withId(R.id.condition_view_description))
                .check(matches(withText("test2")));
    }

    @Test
    public void testDeleteProblem(){
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

        // Go to view problem
        // https://stackoverflow.com/questions/25250061/android-espresso-longclick-is-still-available
        onData(anything()).inAdapterView(withId(R.id.patient_conditions_list_view)).atPosition(0).perform(longClick());
        onView(withText("Yes")).perform(click());


        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            ;
        }

        //Check if problem no longer there in the list
        onView(withId(R.id.condition_view_name))
                .check(doesNotExist());
    }
}
