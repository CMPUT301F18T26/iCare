package com.example.cmput301f18t26.icare;

import android.util.Log;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.UserFactory;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Class was created to test the Patient model class.
 */
public class PatientTest {

    /**
     * Test created to see if UserFactory returns the correct type of user.
     */
    @Test
    public void testCreatePatient() {
        // Loading up the values to test
        int role = 0;
        String username = "username";
        String password = "pass123";
        String email = "fake@email.com";
        String phone = "123-123-1234";

        // Creating a user from the user factory, a patient should be returned
        User u = UserFactory.getUser(username, password, email, phone, role);

        // Checking if a patient is returned.
        assertEquals(u.getClass().getName(), Patient.class.getName());

        role = 1;
        // Creating a user from the user factory, a care provider should be returned
        u = UserFactory.getUser(username, password, email, phone, role);
        // Checking if a patient is returned.
        assertNotEquals(u.getClass().getName(), Patient.class.getName());
    }

    /**
     * Test created to see if add problems works properly in the Patients.
     */
    @Test
    public void testAddProblem() {
        // First we need a Patient
        int role = 0;
        String username = "username";
        String password = "pass123";
        String email = "fake@email.com";
        String phone = "123-123-1234";

        // Creating a user from the user factory, a patient should be returned
        Patient p = (Patient)UserFactory.getUser(username, password, email, phone, role);

        String id = "123";
        // Now we try to add a problemId to the list that is maintained internally by Patient
        p.addProblem(id);
        // Now we check to see if the problem was added to the list
        ArrayList<String> p_ids = p.getProblems();
        assertEquals(p_ids.get(0), id);
        // We add again and check
        String id_two = "ksdflaj";
        p.addProblem(id_two);
        p_ids = p.getProblems();
        assertEquals(p_ids.get(0), id);
        assertEquals(p_ids.get(1), id_two);
    }

    /**
     * Test create to see if the deleteProblem method works right.
     */
    @Test
    public void testDeleteProblem() {
        // First we need a Patient
        int role = 0;
        String username = "username";
        String password = "pass123";
        String email = "fake@email.com";
        String phone = "123-123-1234";

        // Creating a user from the user factory, a patient should be returned
        Patient p = (Patient)UserFactory.getUser(username, password, email, phone, role);

        // Preparing to test.
        String id = "123";
        String id_two = "ksdflaj";
        // Now we try to add a problemId to the list that is maintained internally by Patient
        p.addProblem(id);
        p.addProblem(id_two);
        // Now we check to see if the problem was added to the list
        ArrayList<String> p_ids = p.getProblems();

        // Now deleting an id
        p.deleteProblem(id_two);
        // Now checking to see if it exists
        // It shouldn't
        p_ids = p.getProblems();
        assertEquals(p_ids.indexOf(id_two), -1);
        assertEquals(p_ids.size(), 1);
        // Checking to see if non deleted exists
        assertEquals(p_ids.indexOf(id), 0);
        // Deleting that last one and checking to see if it exists and the size of the array
        p.deleteProblem(id);
        assertEquals(p_ids.indexOf(id), -1);
        // Checking to see if non deleted exists
        assertEquals(p_ids.size(), 0);
    }
}
