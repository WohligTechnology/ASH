package com.wohlig.jaipurpinkpanthers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.wohlig.jaipurpinkpanthers.fragments.GalleryFragment;
import com.wohlig.jaipurpinkpanthers.fragments.HomeFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NavigationDrawerFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NewsDetailFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NewsFragment;
import com.wohlig.jaipurpinkpanthers.fragments.PanthersFragment;
import com.wohlig.jaipurpinkpanthers.fragments.PlayerDescriptionFragment;
import com.wohlig.jaipurpinkpanthers.fragments.ScheduleFragment;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

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
    public static int PLAYER_ID = 12;
    public ImageView ivHome, ivSchedule, ivGallery, ivNews, ivPanthers;
    boolean doubleBackToExitPressedOnce = false;
    boolean inMainActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        tvToolbarText = (TextView) findViewById(R.id.toolbar_title);
        tvToolbarText.setTypeface(CustomFonts.getLightFont(this));

        tvToolbarText.setVisibility(View.GONE);

        container = (FrameLayout) findViewById(R.id.container);
        /*container.setBackgroundColor(getResources().getColor(R.color.jppPrimaryColor));*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();

        initializeViews();

        Intent intent = this.getIntent();
        //int fragmentId = 0;

        //fragmentId = if((Integer.parseInt(intent.getStringExtra("FragmentId"))));
        int fragmentId = intent.getIntExtra("FragmentId",0);
        //fragmentId = if(!intent.getStringExtra("FragmentId").isEmpty())

        onNavigationDrawerItemSelected(fragmentId);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragmentss
        if (position == 0) { // home
            if (inMainActivity) {
                homeShow();
            }
        }
        if (position == 1) { // schedule
            if (inMainActivity) {
                scheduleShow();
            }
        }
        if (position == 2) { // gallery
            if (inMainActivity) {
                galleryShow();
            }
        }
        if (position == 3) { // news
            if (inMainActivity) {
                newsShow();
            }
        }
        if (position == 4) { // knowPanthers
            if (inMainActivity) {
                knowPanthersShow();
            }
        }
        if (position == 5) { // tickets

        }
        if (position == 6) { // wallpapers

        }
        if (position == 7) { // about us
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit...", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            getFragmentManager().popBackStack();
        }
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

    public void initializeViews() {

        initializeBottomToolbarViews();
    }

    public void initializeBottomToolbarViews() {
        ivHome = (ImageView) findViewById(R.id.ivHome);
        ivSchedule = (ImageView) findViewById(R.id.ivSchedule);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivNews = (ImageView) findViewById(R.id.ivNews);
        ivPanthers = (ImageView) findViewById(R.id.ivPanthers);
    }

    public void bottomViewClicked(int i) {

        ivHome.setImageResource(R.drawable.ic_bottom_home_color);
        ivSchedule.setImageResource(R.drawable.ic_bottom_schedule_color);
        ivGallery.setImageResource(R.drawable.ic_bottom_gallery_color);
        ivNews.setImageResource(R.drawable.ic_bottom_news_color);
        ivPanthers.setImageResource(R.drawable.ic_bottom_panthers_color);

        if (i == 1) {
            ivHome.setImageResource(R.drawable.ic_bottom_home_white);
        } else if (i == 2) {
            ivSchedule.setImageResource(R.drawable.ic_bottom_schedule_white);
        } else if (i == 3) {
            ivGallery.setImageResource(R.drawable.ic_bottom_gallery_white);
        } else if (i == 4) {
            ivNews.setImageResource(R.drawable.ic_bottom_news_white);
        } else {
            ivPanthers.setImageResource(R.drawable.ic_bottom_panthers_white);
        }

    }

    public void tvOrImage(boolean tv, String header) {
        if (tv) {
            ivToolbarImage.setVisibility(View.GONE);
            tvToolbarText.setVisibility(View.VISIBLE);
            tvToolbarText.setText(header);
        } else {
            tvToolbarText.setVisibility(View.GONE);
            ivToolbarImage.setVisibility(View.VISIBLE);
        }
    }

    public void home(View v) {
        Log.v("JPP", "Home");
        homeShow();
    }

    public void homeShow() {
        tvOrImage(false, "");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();

        //fragmentTransaction.add(R.id.container, homeFragment, "HOME");
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.commit();

        bottomViewClicked(1);
    }

    public void schedule(View v) {
        Log.v("JPP", "Schedule");
        scheduleShow();
    }

    public void scheduleShow() {
        tvOrImage(true, "SCHEDULE");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        //fragmentTransaction.add(R.id.container, scheduleFragment, "SCHEDULE");
        fragmentTransaction.replace(R.id.container, scheduleFragment);
        fragmentTransaction.commit();

        bottomViewClicked(2);
    }

    public void gallery(View v) {
        Log.v("JPP", "Inside Gallery");
        galleryShow();
    }

    public void galleryShow() {
        tvOrImage(true, "GALLERY");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GalleryFragment galleryFragment = new GalleryFragment();

        //fragmentTransaction.add(R.id.container, galleryFragment, "GALLERY");
        fragmentTransaction.replace(R.id.container, galleryFragment);
        fragmentTransaction.commit();

        bottomViewClicked(3);
    }

    public void news(View v) { ////left to do
        Log.v("JPP", "Inside News");
        newsShow();
    }

    public void newsShow() {
        tvOrImage(true, "NEWS & MEDIA");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsFragment newsFragment = new NewsFragment();

        //fragmentTransaction.add(R.id.container, newsFragment, "NEWS");
        fragmentTransaction.replace(R.id.container, newsFragment);
        fragmentTransaction.commit();

        bottomViewClicked(4);
    }

    public void knowPanthers(View v) {
        Log.v("JPP", "Know Your Panthers");
        knowPanthersShow();
    }

    public void knowPanthersShow() {
        tvOrImage(true, "KNOW YOUR PANTHERS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PanthersFragment panthersFragment = new PanthersFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, panthersFragment);
        fragmentTransaction.commit();

        bottomViewClicked(5);
    }

    public void removeFragment(String TAG_FRAGMENT) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void clearBackStackOfFragments(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void pantherDescription(View v) {
        Log.v("JPP", "Panther Description");
        tvOrImage(true, "KNOW YOUR PANTHERS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerDescriptionFragment playerDescriptionFragment = new PlayerDescriptionFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, playerDescriptionFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void newsDetail(View v){
        Log.v("JPP", "News Detail");
        tvOrImage(true, "KNOW YOUR PANTHERS");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, newsDetailFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void setPlayerId(int id) {
        PLAYER_ID = id;
    }

    public static int getPlayerId() {
        return PLAYER_ID;
    }

}
