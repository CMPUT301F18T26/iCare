package com.example.cmput301f18t26.icare;

import android.support.test.rule.ActivityTestRule;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

public class RecordIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddRecord(){
        //Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fake"));
        onView(withId(R.id.login_button)).perform(click());
        //We go straight to the view problem page
        //First we fetch the current user
        DataController dataController = DataController.getInstance();
        User u = dataController.getCurrentUser();
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

        //Click on addRecord
        onView(withId(R.id.add_new_record_button)).perform(click());

        onView(withId(R.id.view_record_title)).perform(typeText("fake"));
        onView(withId(R.id.record_comment)).perform(typeText("123"));

        onView(withId(R.id.record_comment)).perform(closeSoftKeyboard());
        //Save values
        onView(withId(R.id.userRecord_Edit_Button)).perform(click());
    }
}
