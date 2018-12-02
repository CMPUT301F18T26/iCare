package com.example.cmput301f18t26.icare.Models;


import java.util.UUID;

/**
 * This class holds all the information for records that are associated with a problem.
 * This type of record is used only when a CareProvider creates a record for a problem.
 * This is an abstract class, so it will never initialized.
 */
public abstract class BaseRecord {
    private String UID;
    private String title;
    private String date;
    private String comment;
    private int recType;
    //Every record belongs to a problem
    private String problemUID;

    /**
     * The initializer, takes the title, date, comment and problemId and creates an object.
     * The problemId is the id of a problem that the record is associated with.
     * @param date
     * @param comment
     * @param problemUID
     * @param title
     */
    public BaseRecord(String date, String comment, String problemUID, String title){
        this.UID = UUID.randomUUID().toString();
        this.title = title;
        this.date = date;
        this.comment = comment;
        this.problemUID = problemUID;
    }

    // Getters and setter
    public String getUID() { return this.UID; }

    public void setUID(String UID) { this.UID = UID; }

    public String getProblemUID() { return this.problemUID; }

    public void setProblemId(String problemId) { this.problemUID = problemId; }

    public String getTitle(){return this.title;}

    public void setTitle(String title) {this.title = title;}

    public String getDate() {return this.date;}

    public void setDate(String date) {this.date = date;}

    public String getComment() {return this.comment;}

    public void setComment(String comment) {this.comment = comment;}

    public int getRecType() {
        return recType;
    }

    public void setRecType(int recType) {
        this.recType = recType;
    }

    /**
     * Converts the model to a string that can be displayed or printed.
     * @return
     */
    @Override
    public String toString(){
        // http://www.ntu.edu.sg/home/ehchua/programming/java/DateTimeCalendar.html
        String date = this.getDate();
        return this.getTitle() + " - " + date;
    }
}
