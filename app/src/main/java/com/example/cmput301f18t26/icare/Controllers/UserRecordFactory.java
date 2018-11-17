package com.example.cmput301f18t26.icare.Controllers;

import android.location.Location;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Models.UserRecord;

import java.util.ArrayList;

public class UserRecordFactory {
        //specify everything on creation
        public static UserRecord getUserRecord(String title, String date, String description, Location location, BodyLocation bodyLocation, ArrayList<String> photos, String problemUID){
            UserRecord record;
            record = new UserRecord(title,date,description,location, bodyLocation,photos,problemUID);
            return record;
        }

        //factory method for creating a record without specifying geo,body or photos
        public static UserRecord getUserRecord(String title, String date, String description, String problemUID){
            UserRecord record;
            //on first creation we set the geo and body locations to null and the photos array to null
            record = new UserRecord(title,date,description,null, null,null,problemUID);
            return record;
        }

        //factory method for creating a record without specifying only photos
        public static UserRecord getUserRecord(String title, String date, String description,ArrayList<String> photos, String problemUID){
            UserRecord record;
            //on first creation we set the geo and body locations to null and the photos array to null
            record = new UserRecord(title,date,description,null, null,photos,problemUID);
            return record;
        }

        //factory method for creating a record without specifying body and photos
        public static UserRecord getUserRecord(String title, String date, String description, BodyLocation bodyLocation, ArrayList<String> photos, String problemUID){
            UserRecord record;
            //on first creation we set the geo and body locations to null and the photos array to null
            record = new UserRecord(title,date,description,null, bodyLocation,photos,problemUID);
            return record;
        }

        //factory method for creating a record without specifying geo and photos
        public static UserRecord getUserRecord(String title, String date, String description, Location location, ArrayList<String> photos, String problemUID){
            UserRecord record;
            //on first creation we set the geo and body locations to null and the photos array to null
            record = new UserRecord(title,date,description,location, null,photos,problemUID);
            return record;
        }
}
