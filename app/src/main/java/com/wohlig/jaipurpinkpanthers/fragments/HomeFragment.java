package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

public class HomeFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    LinearLayout llLatestUpdate, llNews, llTable;
    ImageView ivT1, ivT2;
    TextView tvS1, tvS2, tvCo, tvVenue, tvTime;
    TextView tvNewsHead, tvNewsDesc, tvNewsDate, tvNewsRead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

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

        llLatestUpdate = (LinearLayout) view.findViewById(R.id.llLatestUpdate);
        TextView tvLatest = (TextView) llLatestUpdate.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvLatest.setText("LATEST UPDATE");

        llNews = (LinearLayout) view.findViewById(R.id.llNews);
        TextView tvNews = (TextView) llNews.findViewById(R.id.tvCrossHeader);
        tvNews.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvNews.setText("NEWS");

        llTable = (LinearLayout) view.findViewById(R.id.llTable);
        TextView tvTable = (TextView) llTable.findViewById(R.id.tvCrossHeader);
        tvTable.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTable.setText("TABLE");


        tvNo.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvP.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvW.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvL.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvPts.setTypeface(CustomFonts.getRegularFont(getActivity()));

        ivT1 = (ImageView) view.findViewById(R.id.ivT1);
        ivT2 = (ImageView) view.findViewById(R.id.ivT2);

        tvS1 = (TextView) view.findViewById(R.id.tvS1);
        tvS2 = (TextView) view.findViewById(R.id.tvS2);
        tvCo = (TextView) view.findViewById(R.id.tvCo);
        tvS1.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvS2.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvCo.setTypeface(CustomFonts.getBoldFont(getActivity()));

        tvVenue = (TextView) view.findViewById(R.id.tvVenue);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvVenue.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvTime.setTypeface(CustomFonts.getLightFont(getActivity()));

        tvNewsHead = (TextView) view.findViewById(R.id.tvNewsHead);
        tvNewsDesc = (TextView) view.findViewById(R.id.tvNewsDesc);
        tvNewsDate = (TextView) view.findViewById(R.id.tvNewsDate);
        tvNewsRead = (TextView) view.findViewById(R.id.tvNewsRead);

        tvNewsHead.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvNewsDesc.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvNewsDate.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvNewsRead.setTypeface(CustomFonts.getLightFont(getActivity()));

    }
}
