package com.example.cmput301f18t26.icare.Controllers;

import com.example.cmput301f18t26.icare.Problem;
import com.example.cmput301f18t26.icare.Record;
import java.util.ArrayList;

class DataController {
    private ArrayList<Record> records;
    private ArrayList<Problem> problems;


    public DataController() {
    }

    public void fetch(){

        // fetch: this method makes a GET request to ElasticSearch and grabs the latest data
        // pertaining to our user, overwriting our current cached data.

    }

    public String save(){
        // save: this method makes a POST/Patch request to ElasticSearch to merge our locally
        // cached data changes with what is stored in our hosted database.

        //Returns a string which is the id of the object saved given by elastic search
        return null;
    }

    //Records getter, add and remove functions
    public ArrayList<Record> getRecords(){ return this.records = records;}

    public void addRecord(Record record){ this.records.add(record);}

    public void removeRecord(Record record){ this.records.remove(record);}

    //Problems getter, add and remove functions
    public ArrayList<Problem> getProblems(){ return this.problems = problems;}

    public void addProblem(Problem problem){ this.problems.add(problem);}

    public void removeProblem(Problem problem){ this.problems.remove(problem);}

}
