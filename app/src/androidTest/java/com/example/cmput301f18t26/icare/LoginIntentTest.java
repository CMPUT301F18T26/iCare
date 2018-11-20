package com.example.cmput301f18t26.icare;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t26.icare.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    /**
     * Test with right login information
     */
    @Test
    public void testLogin(){
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fake"));
        onView(withId(R.id.password_entry)).perform(typeText("123"));
        onView(withId(R.id.login_button)).perform(click());
        // Should have gone through, Problems label should be there.
        onView(withId(R.id.patient_conditions_label))
                .check(matches(withText(containsString("Problems"))));
    }

    /**
     * Test with wrong login information
     */
    @Test
    public void testWrongLogin(){
        // Logging in
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username_entry)).perform(typeText("fakesadfkljdsalkjdfklslkdsaf"));
        onView(withId(R.id.password_entry)).perform(typeText("12saefjsadlkfdsklfdjsakl3"));
        onView(withId(R.id.login_button)).perform(click());
        // SHould not have gone through, login button should still be there
        onView(withId(R.id.login_button))
                .check(matches(withText(containsString("Log In"))));
    }
}
