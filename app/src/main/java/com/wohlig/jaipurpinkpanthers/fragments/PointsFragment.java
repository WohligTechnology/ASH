package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

public class PointsFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    ListView lvTeams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_points, container, false);

        initilizeViews();

        return view;
    }

    public void initilizeViews() {
        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);

        tvNo.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvP.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvW.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvL.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvPts.setTypeface(CustomFonts.getRegularFont(getActivity()));
    }


}
