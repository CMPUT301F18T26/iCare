package com.example.cmput301f18t26.icare;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testGetSet(){
        String name = "Sample User";
        int role = 0;
        User u = new User(name, role);

        // Name getter and setter
        assertEquals(u.getName(), name);
        name = "New Name";
        u.setName(name);
        assertEquals(u.getName(), name);

        // Role getter and setter
        assertEquals(u.getRole(), role);
        role = 1;
        u.setRole(role);
        assertEquals(u.getRole(), role);
    }
}
