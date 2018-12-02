package com.example.cmput301f18t26.icare.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Class was created to store an image for a record. You pass in a bitmap and a uuid, and the constructor converts the bitmap to a base64 string and stores it.
 */
public class ImageAsString {
    private String photo;
    private String UID;

    /**
     * The constructor, a bitmap of the photo and the UID is passed in. The constructor converts the bitmap to a base64 strings an stores it.
     * @param photo
     * @param UID
     */
    public ImageAsString(Bitmap photo, String UID) {
        // https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
        // Converting bitmap to Base64 string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        this.photo  = Base64.encodeToString(byteArray, Base64.DEFAULT);
        this.UID = UID;
    }

    /**
     * Returns the photo that was stored as a string.
     * @return
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Returns the photo that was stored as a string after converting it to a bitmap.
     * @return
     */
    public Bitmap getphotoAsBitmap() {
        // https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
        byte [] decodedBytes = Base64.decode(photo, Base64.DEFAULT);

        Bitmap temp =  BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        return temp;
    }

    /**
     * Returns the UID
     * @return
     */
    public String getUID() {
        return UID;
    }
}
