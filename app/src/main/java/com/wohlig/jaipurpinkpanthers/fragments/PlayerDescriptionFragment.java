package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wohlig.jaipurpinkpanthers.MainActivity;
import com.wohlig.jaipurpinkpanthers.R;

public class PlayerDescriptionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_description, container, false);

        int id = ((MainActivity)this.getActivity()).getPlayerId();

        Log.e("JPP",String.valueOf(id));

        return view;
    }

}
