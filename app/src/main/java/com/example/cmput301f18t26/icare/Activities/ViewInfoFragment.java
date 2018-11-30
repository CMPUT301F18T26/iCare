package com.example.cmput301f18t26.icare.Activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t26.icare.Controllers.DataController;
import com.example.cmput301f18t26.icare.GestureHelper;
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewInfoFragment extends Fragment {

    private DataController dataController;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateStamp;
    private Problem selectedProblem;
    private ImageView imageView;
    private User user;
    private BaseRecord selectedRecord;
    private boolean imagesLoaded = false;
    private ArrayList<Bitmap> bitMapOfImages = new ArrayList<Bitmap>();
    private ProgressBar progressBar;
    private int imageDisplayedRightNow;
    private boolean playingSlideShow;
    private Handler slideShowHandler;
    private Handler imageLoadHandler;
    private Runnable slideShowRunnable;
    private Runnable imageLoadRunnable;

    Calendar cal = Calendar.getInstance();
    Date date=cal.getTime();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
    String formattedDate=dateFormat.format(date);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No slide show when starting
        playingSlideShow = true;

        dataController = DataController.getInstance();
        user = dataController.getCurrentUser();

        //passing the problem ID not sure if we will need this - tyler
        selectedProblem = dataController.getSelectedProblem();

        // We need to get the imageView
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_info, container, false);

        //Get everything we need for the View
        titleText = (TextView) rootView.findViewById(R.id.view_record_title);
        descriptionText = (TextView) rootView.findViewById(R.id.record_comment);
        dateStamp =  rootView.findViewById(R.id.record_date_and_time);
        dateStamp.setText(formattedDate);

        // Loading bar
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        // Image view to gone
        imageView = (ImageView) rootView.findViewById(R.id.add_photos_to_record);
        imageView.setVisibility(View.GONE);

        this.selectedRecord = dataController.getSelectedRecord();

        // Now getting all the imageView if this is a user record
        if (this.selectedRecord.getRecType() == 0){
            // Getting the record as a user record
            UserRecord userRec = (UserRecord) this.selectedRecord;
            final List<String> userPhotos = userRec.getPhotos();

            // Doing this on another thread
            // https://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java
            // Getting all images from server
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Now fetching the photos
                    for (String photo: userPhotos) {
                        ImageAsString ias = dataController.getPhoto(photo);
                        // Getting the image as a bitmap
                        Bitmap imageBitmap = ias.getphotoAsBitmap();
                        // Adding the photo to array of bitmaps
                        bitMapOfImages.add(imageBitmap);
                    }
                    // So another handler later on is not created
                    imagesLoaded = true;
                }
            });
            t1.start();
            // Need to create a check handler here or it is never created
            createCheckImagesDownloadedHandler();
        } else {
            // Photos gone
            rootView.findViewById(R.id.add_photos_to_record).setVisibility(View.GONE);
            rootView.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }


        setValues(this.selectedRecord);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // Needs to be overridden so we can stop handlers
        cancelHandlers();
    }

    // https://stackoverflow.com/questions/6425611/android-run-a-task-periodically
    private void createCheckImagesDownloadedHandler(){
        imageLoadHandler=new Handler();

        imageLoadRunnable = new Runnable() {
            @Override
            public void run() {
                checkIfDone();
            }
        };

        imageLoadHandler.postDelayed(imageLoadRunnable,100);
    }

    private void changeImageHandlerDuringSlideShow(){
        slideShowHandler=new Handler();
        slideShowRunnable = new Runnable() {
            @Override
            public void run() {
                slideShowImageShow();
            }
        };

        slideShowHandler.postDelayed(slideShowRunnable,2500);

    }

    private void slideShowImageShow() {
        if (playingSlideShow == true && bitMapOfImages.size() > 0) {
            // CHecking if the number got too big
            if (imageDisplayedRightNow >= bitMapOfImages.size()){
                imageDisplayedRightNow = 0;
            }

            // Switching image
            imageView.setImageBitmap(bitMapOfImages.get(imageDisplayedRightNow));
            Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (bitMapOfImages.size()), Toast.LENGTH_SHORT).show();

            // Incrementing the image
            imageDisplayedRightNow++;
            // Starting a handler to swich image
            changeImageHandlerDuringSlideShow();
        }
    }

    private void cancelHandlers(){
        // So the app doesn't crash
        if (slideShowHandler != null && imageLoadHandler != null){
            slideShowHandler.removeCallbacks(slideShowRunnable);
            imageLoadHandler.removeCallbacks(imageLoadRunnable);
        }
    }

    //
    @SuppressLint("ClickableViewAccessibility")
    private void checkIfDone(){
        if (imagesLoaded == false ){
            createCheckImagesDownloadedHandler();
        } else {
            // Now we take away the loading bar
            progressBar.setVisibility(View.GONE);
            // Now we display
            imageView.setVisibility(View.VISIBLE);
            // Starting slideshow
            // First image is being displayed, since slideShowImageShow i
            imageDisplayedRightNow = 0;
            if (bitMapOfImages.size() > 0) {
                Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (bitMapOfImages.size()), Toast.LENGTH_SHORT).show();
                slideShowImageShow();
            }

            imageView.setOnTouchListener(new GestureHelper(getActivity()) {
                public void onClick() {
                    // Start slide show if it is stopped
                    if (playingSlideShow == false && bitMapOfImages.size() > 0){
                        Toast.makeText(getActivity(), "Playing Slideshow", Toast.LENGTH_SHORT).show();
                        playingSlideShow = true;
                        slideShowImageShow();
                    } else if (bitMapOfImages.size() > 0){
                        Toast.makeText(getActivity(), "Slideshow Stopped", Toast.LENGTH_SHORT).show();
                        // Stop slide show
                        playingSlideShow = false;
                    }
                };
                public void onSwipeRight() {
                    if (bitMapOfImages.size() > 0) {
                        imageDisplayedRightNow--;
                        if (imageDisplayedRightNow < 0){
                            imageDisplayedRightNow++;
                        }
                        imageView.setImageBitmap(bitMapOfImages.get(imageDisplayedRightNow));
                        Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (bitMapOfImages.size()), Toast.LENGTH_SHORT).show();
                    }
                }
                public void onSwipeLeft() {
                    if (bitMapOfImages.size() > 0) {
                        imageDisplayedRightNow++;
                        if (imageDisplayedRightNow >= bitMapOfImages.size()){
                            imageDisplayedRightNow--;
                        }
                        imageView.setImageBitmap(bitMapOfImages.get(imageDisplayedRightNow));
                        Toast.makeText(getActivity(), "Displaying: " + (imageDisplayedRightNow + 1) + "/" + (bitMapOfImages.size()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    void setValues(BaseRecord record) {
        //Title
        titleText.setText(record.getTitle());
        //Description
        descriptionText.setText(record.getComment());

        //Date
        String strdate = record.getDate();
        dateStamp.setText(strdate);
    }

}
