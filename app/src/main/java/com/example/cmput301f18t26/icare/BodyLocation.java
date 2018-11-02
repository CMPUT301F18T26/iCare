package com.example.cmput301f18t26.icare;

public enum BodyLocation {

    //enum types will be expanded once BodyLocation selection image has been decided on
    HEAD ("Head"),
    TORSO ("Torso"),
    LEGS ("Legs"),
    BACK ("Back"),
    HANDS ("Hands"),
    FEET ("Feet");

    //
    private Record record;
    private String bodyLocation;

    BodyLocation(String bodyLocation){
        this.bodyLocation = bodyLocation;
    }

    public String toString(){return this.bodyLocation;}
    public void setBodyLocation(String bodyLocation) { this.bodyLocation = bodyLocation;}
}
