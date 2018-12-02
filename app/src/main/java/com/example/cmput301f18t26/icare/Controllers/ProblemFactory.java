package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Models.Problem;

import java.util.Calendar;

/**
 * Creates a new problem with the arguments passed in and returns it.
 */

public class ProblemFactory {
    /**
     * Creates a new problem with the arguments passed in and returns it.
     * @param title
     * @param date
     * @param description
     * @param userUID
     * @return
     */
    public static Problem getProblem(String title, Calendar date, String description, String userUID){
        Problem problem = new Problem(title, date, description, userUID);
        return problem;
    }
}
