package com.example.cmput301f18t26.icare;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewProblemIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAddProblem(){
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
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fake"));
        onView(withId(R.id.login_button)).perform(click());
        //We go straight to the view problem page
        //First we fetch the current user
        DataController dataController = DataController.getInstance();
        User u = dataController.getCurrentUser();

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
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fake"));
        onView(withId(R.id.login_button)).perform(click());
        //We go straight to the view problem page
        //First we fetch the current user
        DataController dataController = DataController.getInstance();
        User u = dataController.getCurrentUser();

        //Go to view problem
        onData(anything()).inAdapterView(withId(R.id.patient_conditions_list_view)).atPosition(0).perform(click());
        // Go to delete problem
        onView(withId(R.id.delete_condition_button)).perform(click());

        //Check if problem no longer there in the list
        onView(withId(R.id.condition_view_name))
                .check(doesNotExist());
    }
}
