package com.example.cmput301f18t26.icare.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cmput301f18t26.icare.R;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cmput301f18t26.icare.R;

public class ViewGeolocationFragment extends Fragment{
    // ATTENTION! WHEN WORKING IN HERE I WOULD USE MY InfoFragment AS A REFERENCE FOR HOW TO WORK
    // IN A FRAGMENT. WHAT IS HERE RIGHT NOW IS JUST A PLACEHOLDER UNTIL EVERYTHING ELSE WAS
    //ACTUALLY IMPLEMENTED. THANKS - TYLER
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_geolocation, null);
    }
}