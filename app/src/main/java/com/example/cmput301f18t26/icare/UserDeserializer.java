package com.example.cmput301f18t26.icare;

import android.util.Log;

import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.Patient;
import com.example.cmput301f18t26.icare.Models.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Deserializer was created to deserialize Patient and Care provider properly
 */

public class UserDeserializer implements JsonDeserializer {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // First getting the JSON object
        JsonObject jsonObject = json.getAsJsonObject();
        // Not getting the types of each object
        JsonElement role = jsonObject.get("role");
        // Now we check to see if type was actually fetched
        if (role != null){
            Log.i("role", role.getAsString());
            // If it was fetched, we start demoralizing data properly
            switch (role.getAsInt()){
                // Now we deal with each type of emotion
                case 0:
                    return context.deserialize(jsonObject, Patient.class);
                case 1:
                    return context.deserialize(jsonObject, CareProvider.class);
            }
        }
        return null;
    }
}
