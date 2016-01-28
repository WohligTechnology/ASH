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

import com.wohlig.jaipurpinkpanthers.fragments.AlbumFragment;
import com.wohlig.jaipurpinkpanthers.fragments.GalleryFragment;
import com.wohlig.jaipurpinkpanthers.fragments.HomeFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NavigationDrawerFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NewsDetailFragment;
import com.wohlig.jaipurpinkpanthers.fragments.NewsFragment;
import com.wohlig.jaipurpinkpanthers.fragments.PanthersFragment;
import com.wohlig.jaipurpinkpanthers.fragments.PlayerDescriptionFragment;
import com.wohlig.jaipurpinkpanthers.fragments.ScheduleFragment;
import com.wohlig.jaipurpinkpanthers.util.CalendarEvent;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private ImageView ivToolbarImage;
    public static TextView tvToolbarText;
    private FrameLayout container;
    public static int PLAYER_ID = 12;
    public static int GALLERY_ID = 0;
    public ImageView ivHome, ivSchedule, ivGallery, ivNews, ivPanthers;
    boolean doubleBackToExitPressedOnce = false;
    boolean inMainActivity = true;
    public static HashMap<String,String> NEWSDETAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        /*mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_panther));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });*/

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        int fragmentId = intent.getIntExtra("FragmentId", 0);
        //fragmentId = if(!intent.getStringExtra("FragmentId").isEmpty())

        onNavigationDrawerItemSelected(fragmentId);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("JPP inside", "oOIS");
        Log.e("JPP item", String.valueOf(item.getItemId()));

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }*/

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        if (position == 0) { // home
            if (inMainActivity) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homeShow();
                    }
                }, 300);
            }
        }
        if (position == 1) { // schedule
            if (inMainActivity) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scheduleShow();
                    }
                }, 300);
            }
        }
        if (position == 2) { // gallery
            if (inMainActivity) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        galleryShow();
                    }
                }, 300);
            }
        }
        if (position == 3) { // news
            if (inMainActivity) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsShow();
                    }
                }, 300);
            }
        }
        if (position == 4) { // knowPanthers
            if (inMainActivity) {
                knowPanthersShow();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        knowPanthersShow();
                    }
                }, 300);
            }
        }
        if (position == 5) { // merchandise
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, MerchandiseActivity.class));
                    finish();
                }
            }, 300);
        }
        if (position == 6) { // wallpapers
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, WallpaperActivity.class));
                    finish();
                }
            }, 300);
        }
        if (position == 7) { // points table
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, PointsActitivy.class));
                    finish();
                }
            }, 300);
        }
        if (position == 8) { // about us
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    finish();
                }
            }, 300);
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

        String tag = v.getTag().toString();
        List<String> playerInfoList = Arrays.asList(tag.split("#"));

        int id = Integer.parseInt(playerInfoList.get(0));
        String name = playerInfoList.get(1);

        setPlayerId(id);

        tvOrImage(true, name);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerDescriptionFragment playerDescriptionFragment = new PlayerDescriptionFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, playerDescriptionFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void newsDetail(View v) {
        Log.v("JPP", "News Detail");
        String tag = v.getTag().toString();
        List<String> newsInfoList = Arrays.asList(tag.split("#"));

        String title = newsInfoList.get(0);
        String image = newsInfoList.get(1);
        String date = newsInfoList.get(2);
        String desc = newsInfoList.get(3);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", title);
        map.put("date", date);
        map.put("desc", desc);
        map.put("image", image);

        setNewsDetails(map);

        tvOrImage(true, "NEWS & MEDIA");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, newsDetailFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void goToGallery(View v) {
        String tag = v.getTag().toString();
        List<String> galleryInfoList = Arrays.asList(tag.split("#"));

        int id = Integer.parseInt(galleryInfoList.get(0));
        String name = galleryInfoList.get(1);
        setGalleryId(id);

        tvOrImage(true, name);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AlbumFragment albumFragment = new AlbumFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, albumFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void setNewsDetails(HashMap<String,String> news) {
        NEWSDETAIL = news;
    }

    public static HashMap<String,String> getNewsDetails() {
        return NEWSDETAIL;
    }

    public static void setGalleryId(int id) {
        GALLERY_ID = id;
    }

    public static int getGalleryId() {
        return GALLERY_ID;
    }

    public static void setPlayerId(int id) {
        PLAYER_ID = id;
    }

    public static int getPlayerId() {
        return PLAYER_ID;
    }

    public static void setToolbarText(String text) {
        tvToolbarText.setText(text);
    }

    public void addToCalendar(View v){
        String tag = v.getTag().toString();
        CalendarEvent.remind(this, tag);
    }

    public void goToBookTickets(View v){
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("webLink", "http://in.bookmyshow.com/sports/kabaddi/jaipur-pink-panthers/");
        startActivity(intent);
    }

}
