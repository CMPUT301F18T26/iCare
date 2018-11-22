package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;

/**
 * This factory produces CareProviders and Patients. It is used anywhere a User needs to be created
 * (Sign up page)
 *
 * This class implements the Factory design pattern, allowing us to instantiate sub-classes of our
 * super-class User, while abstracting them as Users
 */
public class UserFactory {
    public static User getUser(String username, String email, String phone, int role,  String careProviderUID) {
        User user;
        // we are making a Patient
        if (role == 0) {
            user = new Patient(username, email, phone, role, careProviderUID);
        }
        // we are making a Care Provider
        else {
            user = new CareProvider(username, email, phone, role);
        }
        return user;
    }
}


