package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

public class ScheduleFragment extends Fragment {

    View view;
    LinearLayout llUpcomingMatch;
    ImageView ivT1, ivT2;
    TextView tvVenue, tvTime, tvAddToCalendar;
    LinearLayout llOtherMatches;
    TextView tvBook;
    RelativeLayout llAddToCalendar;

    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        initilizeViews();

        return view;
    }

    public void initilizeViews(){

        llUpcomingMatch = (LinearLayout) view.findViewById(R.id.llUpcomingMatch);
        TextView tvLatest = (TextView) llUpcomingMatch.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvLatest.setText("UPCOMING MATCH");

        ivT1 = (ImageView) view.findViewById(R.id.ivT1);
        ivT2 = (ImageView) view.findViewById(R.id.ivT2);

        tvVenue = (TextView) view.findViewById(R.id.tvVenue);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvAddToCalendar = (TextView) view.findViewById(R.id.tvAddToCalendar);
        tvVenue.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvTime.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvAddToCalendar.setTypeface(CustomFonts.getRegularFont(getActivity()));

        llOtherMatches = (LinearLayout) view.findViewById(R.id.llOtherMatches);
        TextView tvOther = (TextView) llOtherMatches.findViewById(R.id.tvCrossHeader);
        tvOther.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvOther.setText("OTHER MATCHES");

        tvBook = (TextView) view.findViewById(R.id.tvBook);
        tvBook.setTypeface(CustomFonts.getRegularFont(getActivity()));

        llAddToCalendar = (RelativeLayout) view.findViewById(R.id.llAddToCalendar);
        String tag = "Jaipur Pink Panthers#Patna Pirates#30 Jan 2016, 20:00";
        llAddToCalendar.setTag(tag);

    }

}
