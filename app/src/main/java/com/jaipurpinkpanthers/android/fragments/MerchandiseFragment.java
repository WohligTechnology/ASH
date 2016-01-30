package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;

public class MerchandiseFragment extends Fragment {

    View view;
    TextView tvTic, tvBookNow;
    TextView tvMer, tvBuyNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_merchandise, container, false);

        initilizeViews();
        return view;
    }

    public void initilizeViews(){

        tvTic = (TextView) view.findViewById(R.id.tvTic);
        tvMer = (TextView) view.findViewById(R.id.tvMer);
        tvTic.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvMer.setTypeface(CustomFonts.getBoldFont(getActivity()));

        tvBuyNow = (TextView) view.findViewById(R.id.tvBuyNow);
        tvBuyNow.setTypeface(CustomFonts.getRegularFont(getActivity()));

        tvBookNow = (TextView) view.findViewById(R.id.tvBookNow);
        tvBookNow.setTypeface(CustomFonts.getRegularFont(getActivity()));

    }

}
