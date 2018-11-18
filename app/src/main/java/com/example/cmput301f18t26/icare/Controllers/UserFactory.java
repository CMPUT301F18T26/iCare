package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;

/**
 * This factory produces CareProviders and Patients. It is used anywhere a User needs to be created
 * (Sign up page)
 *
 * This class implements the Factory design pattern, allowing us to instantiate sub-classes of our
 * super-class User, while abstracting them as Users
 */
public class UserFactory {
    public static User getUser(String username, String password, String email, String phone, int role) {
        User user;
        // we are making a Patient
        if (role == 0) {
            user = new Patient(username, password, email, phone, role);
        }
        // we are making a Care Provider
        else {
            user = new CareProvider(username, password, email, phone, role);
        }
        return user;
    }
}


