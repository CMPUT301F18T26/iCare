package com.example.cmput301f18t26.icare.Controllers;

class DataController {
    private static final DataController ourInstance = new DataController();

    static DataController getInstance() {
        return ourInstance;
    }

    private DataController() {
    }
}
