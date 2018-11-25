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
import com.example.cmput301f18t26.icare.Models.BaseRecord;
import com.example.cmput301f18t26.icare.Models.ImageAsString;
import com.example.cmput301f18t26.icare.Models.Problem;
import com.example.cmput301f18t26.icare.Models.User;
import com.example.cmput301f18t26.icare.Models.UserRecord;
import com.example.cmput301f18t26.icare.OnSwipeTouchListener;
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
        if (UserRecord.class == this.selectedRecord.getClass()){
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
        }

        // Need to create a check handler here or it is never created
        createCheckHandler();

        setValues(this.selectedRecord);
        return rootView;
    }

    // https://stackoverflow.com/questions/6425611/android-run-a-task-periodically
    private void createCheckHandler(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                voidCheckIfDone();
            }
        },100);
    }

    //
    @SuppressLint("ClickableViewAccessibility")
    private void voidCheckIfDone(){
        if (imagesLoaded == false ){
            createCheckHandler();
        } else {
            // Now we take away the loading bar
            progressBar.setVisibility(View.GONE);
            // Now we display
            imageView.setVisibility(View.VISIBLE);
            // First image is being displayed
            imageDisplayedRightNow = 0;
            imageView.setImageBitmap(bitMapOfImages.get(imageDisplayedRightNow));

            // https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
            imageView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
                public void onSwipeRight() {
                    imageDisplayedRightNow--;
                    if (imageDisplayedRightNow < 0){
                        imageDisplayedRightNow++;
                    }
                    imageView.setImageBitmap(bitMapOfImages.get(imageDisplayedRightNow));
                }
                public void onSwipeLeft() {
                    imageDisplayedRightNow++;
                    if (imageDisplayedRightNow >= bitMapOfImages.size()){
                        imageDisplayedRightNow--;
                    }
                    imageView.setImageBitmap(bitMapOfImages.get(imageDisplayedRightNow));
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
