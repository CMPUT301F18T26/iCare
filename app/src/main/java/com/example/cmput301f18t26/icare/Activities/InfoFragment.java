package com.example.cmput301f18t26.icare.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.BodyLocation;
import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.Controllers.RecordFactory;
import com.example.cmput301f18t26.icare.GestureHelper;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.PermissionRequest;
import com.example.cmput301f18t26.icare.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class InfoFragment extends Fragment{
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private DataController dataController;
    private EditText titleEntry;
    private EditText descriptionEntry;
    private TextView dateStamp;
    private Problem selectedProblem;
    private ImageView imageView;
    private User user;
    private int numberOfImagesTaken = 0;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private Uri imageFileUri;
    private int imageDisplayedRightNow;
    private String title;
    private String bodyLocationString;
    private BodyLocation bodyLocation;
    private LatLng geoLocation = null;


    Calendar cal = Calendar.getInstance();
    Date date=cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy @ hh:mm a");
    String formattedDate=dateFormat.format(date);
    static final String STATE_USER = "user";
    private String mUser = "NewUser";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();

        //passing the problem ID not sure if we will need this - tyler
        selectedProblem = dataController.getSelectedProblem();
    }

    @Override
    public void onPause(){
        super.onPause();
        title = titleEntry.getText().toString().trim();
        Bundle savedInstanceState = new Bundle();
        onSaveInstanceState(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        setHasOptionsMenu(false);
        //Get everything we need for the View
        titleEntry = (EditText) rootView.findViewById(R.id.view_record_title);
        descriptionEntry = (EditText) rootView.findViewById(R.id.record_comment);
        dateStamp =  rootView.findViewById(R.id.record_date_and_time);
        dateStamp.setText(formattedDate);



        //Saves your Record and returns you to the Record List View
        Button saveButton = (Button) rootView.findViewById(R.id.userRecord_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Showing the progress bar
                ProgressBar progressBar = getActivity().findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);

                save();
            }

        });

        // Creating on click listener
        imageView = rootView.findViewById(R.id.add_photos_to_record);

        // If the images have already been taken, displaying them
        if (imageUris.size() > 0){
            imageView.setImageDrawable(Drawable.createFromPath(imageUris.get(imageDisplayedRightNow).getPath()));
            Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (imageUris.size()), Toast.LENGTH_SHORT).show();
        }

        imageView.setOnTouchListener(new GestureHelper(getActivity()) {
            public void onClick() {
                takeAPhoto();
            };
            public void onSwipeRight() {
                imageDisplayedRightNow--;
                if (imageDisplayedRightNow < 0){
                    imageDisplayedRightNow++;
                }
                imageView.setImageDrawable(Drawable.createFromPath(imageUris.get(imageDisplayedRightNow).getPath()));
                Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (imageUris.size()), Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                imageDisplayedRightNow++;
                if (imageDisplayedRightNow >= imageUris.size()){
                    imageDisplayedRightNow = imageUris.size() - 1;
                }
                imageView.setImageDrawable(Drawable.createFromPath(imageUris.get(imageDisplayedRightNow).getPath()));
                Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (imageUris.size()), Toast.LENGTH_SHORT).show();
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
            // Incrementing number of imageView taken
            numberOfImagesTaken++;
            // imageUris storage
            imageUris.add(imageFileUri);

            // Showing what photo was taken
            imageView = getActivity().findViewById(R.id.add_photos_to_record);
            imageView.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));

            //https://developer.android.com/guide/topics/ui/dialogs
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Would you list to add more imageView? You have taken " + numberOfImagesTaken + " image(s).").setTitle("More Images");

            // The person wants to take another photo
            builder.setPositiveButton("Another One", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    takeAPhoto();
                }
            });

            // Done taking imageView
            builder.setNegativeButton("No More", new DialogInterface.OnClickListener() {
                @SuppressLint("ClickableViewAccessibility")
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    imageDisplayedRightNow = imageUris.size() - 1;
                    imageView.setImageDrawable(Drawable.createFromPath(imageUris.get(imageDisplayedRightNow).getPath()));
                    Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (imageUris.size()), Toast.LENGTH_SHORT).show();
                    // After an image has been added once, you can no longer edit them and we let the user view the images they have added
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "Something went wrong while getting images.", Toast.LENGTH_SHORT).show();
        }
    }

    public void save(){

        //Retrieves the current body location string and sets the BodyLocation accordingly.
        //If there is no string (ie when you add a record without specifying a body location)
        //the BodyLocation will just be null.
        bodyLocationString = dataController.getCurrentBodyLocation();
        if (bodyLocationString != null){
            bodyLocation = BodyLocation.valueOf(bodyLocationString);
            //This removes the selected body location from DataController, to prevent
            //the body location accidentally being used during future records adds.
            dataController.deleteCurrentBodyLocation();
        }
        else{
            bodyLocation = null;
        }

        geoLocation = dataController.getCurrentGeoLocation();
        //same as deleteCurrentBodyLocation
        dataController.deleteCurrentGeoLocation();

        //Get the values of the Title, Date and Description fields
        title = titleEntry.getText().toString().trim();
        String description = descriptionEntry.getText().toString().trim();
        user = dataController.getCurrentUser();
        String userUID = user.getUID();
        // Since this is a user created record
        int recType = 0;

        ArrayList<String> imageList = new ArrayList<>();
        final ArrayList<ImageAsString> imageAsStringList = new ArrayList<>();

        // Go through all the imageView and get them into memory
        for (Uri uri: imageUris){
            // Getting bitmap storing it in a calss
            Bitmap imageBitmap = BitmapFactory.decodeFile(uri.toString().replace("file:/", ""));
            ImageAsString ias = new ImageAsString(imageBitmap, UUID.randomUUID().toString());
            // Also storing bitmap locally

            // Adding the ids of imageView to a list
            imageList.add(ias.getUID());
            // Adding the imageView to a list
            imageAsStringList.add(ias);
        }

        //Create a new record in the userRecordFactory.
        BaseRecord record = RecordFactory.getRecord(formattedDate, description, selectedProblem.getUID(), geoLocation, bodyLocation, imageList, recType, title);
        dataController.addRecord(record);

        // Sending all imageView to server
        for (ImageAsString iasIter: imageAsStringList){
            dataController.addPhoto(iasIter);
        }

        Toast.makeText(getActivity(), "Added record to the list of records.", Toast.LENGTH_SHORT).show();
        getActivity().setResult(RESULT_OK);

        //Returns to the problem description and list of records for that problem.
        getActivity().finish();
    }

}
