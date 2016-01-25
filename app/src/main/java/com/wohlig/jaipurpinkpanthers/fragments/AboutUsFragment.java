package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

public class AboutUsFragment extends Fragment {

    View view;
    LinearLayout llTeam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_us, container, false);

        initilizeViews();

        return view;
    }

    public void initilizeViews(){
        TextView tvAboutContent = (TextView) view.findViewById(R.id.tvAboutContent);
        tvAboutContent.setTypeface(CustomFonts.getLightFont(getActivity()));

        llTeam = (LinearLayout) view.findViewById(R.id.llTeam);
        TextView tvTeam = (TextView) llTeam.findViewById(R.id.tvCrossHeader);
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setText("TEAM");

    }


}
