package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Models.Problem;

import java.util.Calendar;

/**
 * I am trying to follow Tony's UserFactory as close as possible.
 * -Conor
 */

public class ProblemFactory {
    public static Problem getProblem(String title, Calendar date, String description, String userUID){
        Problem problem;
        problem = new Problem(title, date, description, userUID);
        return problem;
    }
}
