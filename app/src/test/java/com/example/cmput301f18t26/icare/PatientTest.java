package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.UserFactory;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PatientTest {
    /*
    Class was created to test the Patient model class.
     */

    @Test
    public void testAddPatient() {
        // Loading up the values to test
        int role = 0;
        String username = "username";
        String password = "pass123";
        String email = "fake@email.com";
        String phone = "123-123-1234";

        DataController dataController = DataController.getInstance();

        User u = UserFactory.getUser(username, password, email, phone, role);

        dataController.addUser(u);

    }
}
