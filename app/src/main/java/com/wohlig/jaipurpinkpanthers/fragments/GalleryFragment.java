package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

public class GalleryFragment extends Fragment {

    View viewPhotos, viewVideos;
    View view;
    LinearLayout llPhotos, llVideos;
    ViewFlipper vfGallery;
    TextView tvPhotos, tvVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        Log.v("JPP", "Inside gallery oCV");

        initializeViews();
        setListeners();
        return view;
    }


    public void initializeViews(){

        viewPhotos = view.findViewById(R.id.viewPhotos);
        viewVideos = view.findViewById(R.id.viewVideos);
        viewPhotos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

        tvPhotos = (TextView) view.findViewById(R.id.tvPhotos);
        tvVideos = (TextView) view.findViewById(R.id.tvVideos);
        tvPhotos.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvVideos.setTypeface(CustomFonts.getLightFont(getActivity()));

        llPhotos = (LinearLayout) view.findViewById(R.id.llPhotos);
        llVideos = (LinearLayout) view.findViewById(R.id.llVideos);

        vfGallery = (ViewFlipper) view.findViewById(R.id.vfGallery);
        vfGallery.setFlipInterval(500);

    }

    public void setListeners(){

        llPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewVideos.setBackgroundColor(getResources().getColor(R.color.jppGalleryToolbar));
                viewPhotos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

                if (vfGallery.getDisplayedChild() != 0)
                    vfGallery.showPrevious();
            }
        });

        llVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPhotos.setBackgroundColor(getResources().getColor(R.color.jppGalleryToolbar));
                viewVideos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

                if (vfGallery.getDisplayedChild() != 1)
                    vfGallery.showNext();
            }
        });
    }

}
