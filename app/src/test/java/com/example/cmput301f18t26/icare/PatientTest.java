package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.UserFactory;
import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PatientTest {
    /**
     * Test was created to test the role that a care provider has
     */
    @Test
    public void testRoleForPatient() {
        // Creating a care provider
        User user = new Patient("user", "wash@fake.com", "123-123-1234", "");
        // Checking the role returned
        assertEquals(user.getRole(), 0);
    }

    /**
     * Now using user factory to test the role for a care provider.
     */
    @Test
    public void testRoleForCareProviderUserFactor() {
        // Creating a care provider
        User user = UserFactory.getUser("user", "wash@fake.com", "123-123-1234", 0,  "");
        // Checking the role returned
        assertEquals(user.getRole(), 0);
        assertEquals(user.getClass(), Patient.class);
    }

    /**
     * Check that a care provider id can be added
     */
    @Test
    public void testCareProviderAddingId() {
        // Creating a care provider
        Patient user = (Patient)UserFactory.getUser("user", "wash@fake.com", "123-123-1234", 0,  "");
        // Checking the role returned
        assertEquals(user.getRole(), 0);
        assertEquals(user.getClass(), Patient.class);
        // Adding teh care provider id
        assertEquals("", user.getCareProviderUID());
        String cpId = "12ekjnsdfa=21oyihkdflasna-ewf8osihdkjn";
        user.setCareProviderUID(cpId);
        // Now check if set
        assertEquals(cpId, user.getCareProviderUID());
    }
}
