package com.example.cmput301f18t26.icare;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.MainActivity;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ViewProfileActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testProfile(){
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fake"));
        onView(withId(R.id.login_button)).perform(click());

        // Getting to the view profile page
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Contact Information")).perform(click());
        // First we fetch the current user
        DataController dataController = DataController.getInstance();
        User u = dataController.getCurrentUser();
        // Now checking if the right info is displayed
        onView(withId(R.id.profile_name))
                .check(matches(withText(containsString(u.getUsername()))));
        onView(withId(R.id.email))
                .check(matches(withText(containsString(u.getEmail()))));
        onView(withId(R.id.phone))
                .check(matches(withText(containsString(u.getPhone()))));
    }

    @Test
    public void editProfile(){
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fake"));
        onView(withId(R.id.login_button)).perform(click());

        // Getting to the view profile page
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Contact Information")).perform(click());
        // First we fetch the current user
        DataController dataController = DataController.getInstance();
        User u = dataController.getCurrentUser();
        // Since we are testing a user loading their own profile, we need to check if th edit profile button is there and click it
        onView(withId(R.id.edit_profile)).perform(click());
        // Now we edit the values
        String newEmail = u.getEmail() + "com";
        String newPhone = u.getPhone() + "1";
        // Now we type the values into the thing
        onView(withId(R.id.email)).perform(replaceText(newEmail));
        onView(withId(R.id.phone)).perform(replaceText(newPhone));
        // Press save
        onView(withId(R.id.save_profile)).perform(click());
        // Now check values again
        // Now checking if the right info is displayed
        onView(withId(R.id.profile_name))
                .check(matches(withText(containsString(u.getUsername()))));
        onView(withId(R.id.email))
                .check(matches(withText(containsString(newEmail))));
        onView(withId(R.id.phone))
                .check(matches(withText(containsString(newPhone))));
        // Done
    }
}
