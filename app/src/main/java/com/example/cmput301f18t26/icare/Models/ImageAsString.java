package com.example.cmput301f18t26.icare.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageAsString {
    private String photo;
    private String UID;

    public ImageAsString(Bitmap photo, String UID) {
        // https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
        // Converting bitmap to Base64 string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        this.photo  = Base64.encodeToString(byteArray, Base64.DEFAULT);
        this.UID = UID;
    }

    public String getPhoto() {
        return photo;
    }

    public Bitmap getphotoAsBitmap() {
        // https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
        byte [] decodedBytes = Base64.decode(photo, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public String getUID() {
        return UID;
    }
}
