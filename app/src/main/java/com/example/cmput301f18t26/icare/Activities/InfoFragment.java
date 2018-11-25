package com.example.cmput301f18t26.icare.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.RecordFactory;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.PermissionRequest;
import com.example.cmput301f18t26.icare.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class InfoFragment extends Fragment{
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private Problem selectedProblem;
    private ImageView images;
    private User user;
    private int numberOfImagesTaken = 0;
    private ArrayList<Bitmap> imageThumbnails;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private Uri imageFileUri;


    Calendar cal = Calendar.getInstance();
    Date date=cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
    String formattedDate=dateFormat.format(date);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();

        //passing the problem ID not sure if we will need this - tyler
        selectedProblem = dataController.getSelectedProblem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        //Get everything we need for the View
        titleEntry = (EditText) rootView.findViewById(R.id.record_title);
        descriptionEntry = (EditText) rootView.findViewById(R.id.record_comment);
        dateStamp =  rootView.findViewById(R.id.record_date_and_time);
        dateStamp.setText(formattedDate);

        //Saves your Record and returns you to the Record List View

        Button saveButton = (Button) rootView.findViewById(R.id.userRecord_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Processing, please wait.", Toast.LENGTH_SHORT).show();
                getActivity().setResult(RESULT_OK);
                save();
                //TODO: add check to make sure values entered correctly
            }

        });

        // Creating on click listener
        images = rootView.findViewById(R.id.add_photos_to_record);
        images.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Adding the photos
                takeAPhoto();
            }
        });

        return rootView;
    }

    public void takeAPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }

        //if(Build.VERSION.SDK_INT>=16) {
        try {
            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
            m.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //}

        PermissionRequest.verifyPermission(getActivity());

        String imageFilePath = UUID.randomUUID().toString() + ".jpg";

        File imageFile = new File(folder,imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    // https://github.com/vingk/cameratest2
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Incrementing number of images taken
            numberOfImagesTaken++;
            // imageUris storage
            imageUris.add(imageFileUri);

            // Showing what photo was taken
            ImageView imageView = getActivity().findViewById(R.id.add_photos_to_record);
            imageView.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));

            //https://developer.android.com/guide/topics/ui/dialogs
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Would you list to add more images? You have taken " + numberOfImagesTaken + " image(s).").setTitle("More Images");

            // The person wants to take another photo
            builder.setPositiveButton("Another One", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    takeAPhoto();
                }
            });

            // Done taking images
            builder.setNegativeButton("No More", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            // After an image has been added once, you can no longer edit them.
            images.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Image(s) have already been added. You can only edit this field once.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Something went wrong while selecting images.", Toast.LENGTH_SHORT).show();
        }
    }

    public void save(){
        //Get the values of the Title, Date and Description fields
        String title = titleEntry.getText().toString().trim();
        String description = descriptionEntry.getText().toString().trim();
        user = dataController.getCurrentUser();
        String userUID = user.getUID();
        // Since this is a user created record
        int recType = 0;

        ArrayList<String> imageList = new ArrayList<>();
        final ArrayList<ImageAsString> imageAsStringList = new ArrayList<>();

        // Go through all the images and get them into memory
        for (Uri uri: imageUris){
            // Getting bitmap storing it in a calss
            ImageAsString ias = new ImageAsString(BitmapFactory.decodeFile(uri.toString().replace("file:/", "")), UUID.randomUUID().toString());
            // Adding the ids of images to a list
            imageList.add(ias.getUID());
            // Adding the images to a list
            imageAsStringList.add(ias);
        }

        //Create a new record in the userRecordFactory.
        BaseRecord record = RecordFactory.getRecord(formattedDate, description, selectedProblem.getUID(), null, null, imageList, recType, title);
        dataController.addRecord(record);
        Toast.makeText(getActivity(), "User Record added successfully", Toast.LENGTH_SHORT).show();

        // https://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java
        // Sending all images to server
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (ImageAsString iasIter: imageAsStringList){
                    dataController.addPhoto(iasIter);
                }
            }
        });
        t1.start();

        //Returns to the problem description and list of records for that problem.
        getActivity().finish();
    }
}
