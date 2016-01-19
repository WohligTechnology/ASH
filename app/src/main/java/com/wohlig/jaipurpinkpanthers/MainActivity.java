package com.wohlig.jaipurpinkpanthers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.fragments.GalleryFragment;
import com.wohlig.jaipurpinkpanthers.fragments.HomeFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NewsFragment;
import com.wohlig.jaipurpinkpanthers.fragments.PanthersFragment;
import com.wohlig.jaipurpinkpanthers.fragments.ScheduleFragment;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private ImageView ivToolbarImage;
    private TextView tvToolbarText;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ivToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        tvToolbarText = (TextView) findViewById(R.id.toolbar_title);

        tvToolbarText.setVisibility(View.GONE);

        container = (FrameLayout) findViewById(R.id.container);
        /*container.setBackgroundColor(getResources().getColor(R.color.jppPrimaryColor));*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.container, homeFragment, "HOME");
        fragmentTransaction.commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragmentss
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void tvOrImage(boolean tv, String header){
        if(tv){
            ivToolbarImage.setVisibility(View.GONE);
            tvToolbarText.setVisibility(View.VISIBLE);
            tvToolbarText.setText(header);
        }else{
            tvToolbarText.setVisibility(View.GONE);
            ivToolbarImage.setVisibility(View.VISIBLE);
        }
    }

    public void home(View v){
        Log.v("JPP", "Home");

        tvOrImage(false, "");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();

        fragmentTransaction.add(R.id.container, homeFragment, "HOME");
        fragmentTransaction.commit();
    }
    public void schedule(View v){
        Log.v("JPP", "Schedule");
        tvOrImage(true,"SCHEDULE");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        fragmentTransaction.add(R.id.container, scheduleFragment, "SCHEDULE");
        fragmentTransaction.commit();
    }

    public void gallery(View v){
        Log.v("JPP", "Inside Gallery");
        tvOrImage(true,"GALLERY");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GalleryFragment galleryFragment = new GalleryFragment();

        fragmentTransaction.add(R.id.container, galleryFragment, "GALLERY");
        fragmentTransaction.commit();
    }

    public void news(View v){ ////left to do
        Log.v("JPP", "Inside News");
        tvOrImage(true,"NEWS & MEDIA");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsFragment newsFragment = new NewsFragment();

        fragmentTransaction.add(R.id.container, newsFragment, "NEWS");
        fragmentTransaction.commit();

    }

    public void knowPanthers(View v){
        Log.v("JPP", "Know Your Panthers");
        tvOrImage(true,"KNOW YOUR PANTHERS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PanthersFragment panthersFragment = new PanthersFragment();

        fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.commit();

    }

    public void removeFragment(String TAG_FRAGMENT){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

}
