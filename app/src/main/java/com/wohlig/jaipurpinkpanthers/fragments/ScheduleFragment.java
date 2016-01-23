package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wohlig.jaipurpinkpanthers.R;

public class ScheduleFragment extends Fragment {


    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        return view;
    }

}
