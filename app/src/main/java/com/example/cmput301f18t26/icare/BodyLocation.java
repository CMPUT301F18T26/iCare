package com.example.cmput301f18t26.icare;

import com.example.cmput301f18t26.icare.Models.UserRecord;

public enum BodyLocation {

    //Front Locations
    FRONT_HEAD ("Front Head"),
    FRONT_NECK_SHOULDERS ("Front Neck and Shoulders"),
    FRONT_TORSO ("Torso"),
    FRONT_STOMACH("Stomach"),
    FRONT_RIGHT_ARM ("Front Right Arm"),
    FRONT_LEFT_ARM ("Front Left Arm"),
    FRONT_RIGHT_LEG ("Front Right Leg"),
    FRONT_LEFT_LEG ("Front Left Leg"),

    //Back Locations
    BACK_HEAD ("Back Head"),
    BACK_NECK_SHOULDERS ("Back Neck and Shoulders"),
    BACK_UPPER_BACK ("Upper Back"),
    BACK_LOWER_BACK ("Lower Back"),
    BACK_RIGHT_ARM ("Back Right Arm"),
    BACK_LEFT_ARM ("Back Left Arm"),
    BACK_RIGHT_LEG ("Back Right Leg"),
    BACK_LEFT_LEG ("Back Left Leg");

    private String id;
    //Every body location belongs to a user record
    private UserRecord userRecord;
    public String bodyLocation;

    BodyLocation(String bodyLocation){
        this.bodyLocation = bodyLocation;
    }

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    public String toString(){return this.bodyLocation;}
    public String getBodyLocation(){return this.bodyLocation;}
    public void setBodyLocation(String bodyLocation) { this.bodyLocation = bodyLocation;}
}
