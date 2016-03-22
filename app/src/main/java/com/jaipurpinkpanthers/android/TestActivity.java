package com.jaipurpinkpanthers.android;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jaipurpinkpanthers.android.adapters.ViewPagerAdapter;

public class TestActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter();

        mViewPager.setAdapter(viewPagerAdapter);

        for(int i = 0; i < 5; i++){

            String stadium = "DOME@NSCI SVP Stadium, Mumbai";
            String score1 = "35";
            String score2 = "21";
            String galleryid =  "17";
            String team1Id = "8";
            String team2Id = "5";

            viewPagerAdapter.addView(addNewView());
        }

        viewPagerAdapter.notifyDataSetChanged();
    }


    private LinearLayout addNewView() {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.layout_review, null);

        return v;
    }

}
