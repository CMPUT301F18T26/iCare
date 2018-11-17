package com.example.cmput301f18t26.icare.Models;

import java.util.Calendar;

public class Record {

    private String id;
    private String title;
    private String date;
    private String comment;
    //Every record belongs to a problem
    private String problemId;


    public Record(String title, String date, String comment,String problemId){
        this.title = title;
        this.date = date;
        this.comment = comment;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getProblemId() { return this.problemId; }

    public void setProblemId(String problemId) { this.problemId = problemId; }

    public String getTitle(){return this.title;}

    public void setTitle(String title) {this.title = title;}

    public String getDate() {return this.date;}

    public void setDate(String date) {this.date = date;}

    public String getComment() {return this.comment;}

    public void setComment(String comment) {this.comment = comment;}


}
