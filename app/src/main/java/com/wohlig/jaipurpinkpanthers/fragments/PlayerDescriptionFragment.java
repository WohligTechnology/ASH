package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.MainActivity;
import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

import java.util.Arrays;
import java.util.List;

public class PlayerDescriptionFragment extends Fragment {
    private View view;
    private LinearLayout llPlayerDesc;
    private ImageView ivPlayer;
    private TextView tvName, tvType, tvNationality, tvDOB, tvJerseyNo, tvDesc, tvNat, tvBorn, tvJer;
    private Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player_description, container, false);

        int id = ((MainActivity) this.getActivity()).getPlayerId();

        //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        initilizeViews();
        getPlayerDetails(id);
        return view;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initilizeViews() {

        activity = getActivity();

        llPlayerDesc = (LinearLayout) view.findViewById(R.id.llPlayerDesc);

        ivPlayer = (ImageView) llPlayerDesc.findViewById(R.id.ivPlayer);
        tvName = (TextView) llPlayerDesc.findViewById(R.id.tvName);
        tvType = (TextView) llPlayerDesc.findViewById(R.id.tvType);
        tvNationality = (TextView) llPlayerDesc.findViewById(R.id.tvNationality);
        tvDOB = (TextView) llPlayerDesc.findViewById(R.id.tvDOB);
        tvJerseyNo = (TextView) llPlayerDesc.findViewById(R.id.tvJerseyNo);
        tvDesc = (TextView) llPlayerDesc.findViewById(R.id.tvDesc);

        tvNat = (TextView) llPlayerDesc.findViewById(R.id.tvNat);
        tvBorn = (TextView) llPlayerDesc.findViewById(R.id.tvBorn);
        tvJer = (TextView) llPlayerDesc.findViewById(R.id.tvJer);

        tvName.setTypeface(CustomFonts.getProfileFont(activity));
        tvType.setTypeface(CustomFonts.getProfileFont(activity));
        tvNationality.setTypeface(CustomFonts.getLightFont(activity));
        tvDOB.setTypeface(CustomFonts.getLightFont(activity));
        tvJerseyNo.setTypeface(CustomFonts.getLightFont(activity));
        tvDesc.setTypeface(CustomFonts.getLightFont(activity));

        tvNat.setTypeface(CustomFonts.getRegularFont(activity));
        tvBorn.setTypeface(CustomFonts.getRegularFont(activity));
        tvJer.setTypeface(CustomFonts.getRegularFont(activity));
    }

    public void getPlayerDetails(int id) {
        String[] players = getResources().getStringArray(R.array.playersDesc);
        String singlePlayer = players[id - 1];

        //String player = getResources().getString(R.string.player1);
        List<String> playerDetail = Arrays.asList(singlePlayer.split("#"));

        String playerName = playerDetail.get(0);                //PlayerName
        String playerType = playerDetail.get(1);                //PlayerType
        String playerNationality = playerDetail.get(2);         //PlayerNationality
        String playerDob = playerDetail.get(3);                 //PlayerDob
        String playerJerseyNum = playerDetail.get(4);           //PlayerJerseyNum
        String playerDesc = playerDetail.get(5);                //PlayerDesc

        TypedArray playerImages = getActivity().getResources().obtainTypedArray(R.array.playerImages);

        ivPlayer.setImageResource(playerImages.getResourceId(id-1, 0));

        tvName.setText(playerName);
        tvNationality.setText(playerNationality);
        tvDOB.setText(playerDob);
        tvJerseyNo.setText(playerJerseyNum);
        tvDesc.setText(playerDesc);

        playerImages.recycle();
    }

}
