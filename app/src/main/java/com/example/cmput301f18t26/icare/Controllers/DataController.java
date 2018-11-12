package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Patient;
import com.example.cmput301f18t26.icare.Problem;
import com.example.cmput301f18t26.icare.Record;
import com.example.cmput301f18t26.icare.CareProvider;
import com.example.cmput301f18t26.icare.User;

import java.util.ArrayList;
import java.util.List;

/* DataController is a Singleton class used for accessing ElasticSearch data associated with
   our app. An instance of it may be retrieved using DataController.getInstance();
 */
class DataController {

    public DataController() {
    }

    public void fetch() {
    }

    public String save(){
        // save: this method makes a POST/Patch request to ElasticSearch to merge our locally
        // cached data changes with what is stored in our hosted database.

        //Returns a string which is the id of the object saved given by elastic search
        return null;
    }

    public Record getRecord(String recordId){
        //get specific record
        return null;
    }

    public List<Record> getRecords(String problemId){
        //get all records associated with the problem
        return null;
    }

    public String addRecord(Record record){
        //add record and return new recordId
        return null;
    }

    public Problem getProblem(String problemid){
        //get specific Problem
        return null;
    }

    public List<Problem> getProblems(String patientId){
        //return all problems for a patient
        return null;
    }

    public String addProblem(Problem problem){
        //add Problem and return new problemId
        return null;
    }

    public User getUser(String userId){
        //get specific Id
        return null;
    }

    public String addUser(User user){
        //add user and return the user id
        return null;
    }

    public List<User> getPatients(String careProviderId){
        //return all patients for a care provider
        return null;
    }
}