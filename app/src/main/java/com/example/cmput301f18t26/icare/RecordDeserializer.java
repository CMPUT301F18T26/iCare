package com.example.cmput301f18t26.icare;

import android.util.Log;

import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.CareProvider;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Deserializer was created to deserialize BaseRecord and UserRecords properly
 */
public class RecordDeserializer implements JsonDeserializer<BaseRecord> {
    @Override
    public BaseRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // First getting the JSON object
        JsonObject jsonObject = json.getAsJsonObject();
        // Not getting the types of each object
        JsonElement recType = jsonObject.get("recType");
        // Now we check to see if type was actually fetched
        if (recType != null){
            Log.i("Error", recType.getAsString());
            // If it was fetched, we start demoralizing data properly
            switch (recType.getAsInt()){
                // Now we deal with each type of emotion
                case 0:
                    return context.deserialize(jsonObject, CareProvider.class);
                case 1:
                    return context.deserialize(jsonObject, UserRecord.class);
            }
        }
        return null;
    }
}
