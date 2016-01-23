package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.wohlig.jaipurpinkpanthers.R;

public class MerchandiseFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_merchandise, container, false);

        initilizeViews();
        return view;
    }

    public void initilizeViews(){
        SliderLayout sliderShow = (SliderLayout) view.findViewById(R.id.slider);

        TextSliderView textSliderView = new TextSliderView(getActivity());
        textSliderView
                //.description("Game of Thrones")
                .image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        TextSliderView textSliderView1 = new TextSliderView(getActivity());
        textSliderView1
                //.description("Game of Thrones123")
                .image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        sliderShow.addSlider(textSliderView);
        sliderShow.addSlider(textSliderView1);

        //sliderShow.stopAutoCycle();

    }

}
