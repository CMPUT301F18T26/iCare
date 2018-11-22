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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupIntentTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSignupUserAlreadyTaken(){
        // Signing up
        onView(withId(R.id.signup_button)).perform(click());
        // User already exists
        onView(withId(R.id.username_entry)).perform(typeText("newuser1"));
        onView(withId(R.id.phone_entry)).perform(typeText("1234567890"));
        onView(withId(R.id.email_entry)).perform(typeText("wasd@gmail.com"));
        onView(withId(R.id.email_entry)).perform(closeSoftKeyboard());
        onView(withId(R.id.role_patient)).perform(click());
        onView(withId(R.id.signup_button)).perform(click());
        // Now we check to see if the button still exists
        onView(withId(R.id.signup_button))
                .check(matches(withText(containsString("Sign Up"))));
    }
}
