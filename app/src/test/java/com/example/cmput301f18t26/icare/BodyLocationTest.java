package com.example.cmput301f18t26.icare;

import org.junit.Test;

import static org.junit.Assert.*;

public class BodyLocationTest {

    @Test
    public void testNewBodyLocationConstructor(){

        String bodyLocation = "Head";
        BodyLocation b = BodyLocation.HEAD;
        assertEquals(bodyLocation, b.bodyLocation);
    }
}