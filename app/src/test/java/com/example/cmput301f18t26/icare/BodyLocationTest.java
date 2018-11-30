package com.example.cmput301f18t26.icare;

import org.junit.Test;

import static org.junit.Assert.*;

public class BodyLocationTest {

    /**
     * Meant to test the constructor
     */
    @Test
    public void testNewBodyLocationConstructor(){
        // Front of head body location
        String bodyLocation = "Front Head";
        BodyLocation b = BodyLocation.FRONT_HEAD;
        assertEquals(bodyLocation, b.getBodyLocation());
    }

    /**
     * Meant to test the setters and getters
     */
    @Test
    public void testBodyLocationSettersAndGetters(){
        // Create a body location object
        String bodyLocation = "Front Head";
        BodyLocation b = BodyLocation.FRONT_HEAD;
        assertEquals(bodyLocation, b.getBodyLocation());
        // Set it to something different
        bodyLocation = "Back Left Arm";
        b.setBodyLocation(BodyLocation.BACK_LEFT_ARM.toString());
        // Now checking
        assertEquals(bodyLocation, b.getBodyLocation());
    }
}