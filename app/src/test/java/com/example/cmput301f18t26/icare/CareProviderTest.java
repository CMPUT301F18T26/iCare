package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Controllers.UserFactory;
import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CareProviderTest {
    /**
     * Test was created to test the role that a care provider has
     */
    @Test
    public void testRoleForCareProvider() {
        // Creating a care provider
        User user = new CareProvider("user", "wash@fake.com", "123-123-1234");
        // Checking the role returned
        assertEquals(user.getRole(), 1);
    }

    /**
     * Now using user factory to test the role for a care provider.
     */
    @Test
    public void testRoleForCareProviderUserFactor() {
        // Creating a care provider
        User user = UserFactory.getUser("user", "wash@fake.com", "123-123-1234", 1,  "");
        // Checking the role returned
        assertEquals(user.getRole(), 1);
        assertEquals(user.getClass(), CareProvider.class);
    }
}
