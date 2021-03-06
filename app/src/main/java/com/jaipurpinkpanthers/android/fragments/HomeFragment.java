package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.ViewPagerAdapter;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts,tvmonth,tvdays,tvhours,tvmins;
    LinearLayout llLatestUpdate, llNews, llTable, llPoints,llupcomingmatch, lljpptv, llsignUp,llLiveupdate;
    ImageView ivT1, ivT2, ivNews, ivHomeMain,llivT1live,llivT2live;
    int tvS1,tvS2;
    //TextView tvS2,tvS1;
    TextView tvCo;
    TextView tvVenue;
    TextView tvTime;
    TextView tvMatchTime;
    TextView tvlivescore1;
    TextView tvlivescore2;
    TextView hftime;
    TextView tvNewsHead, tvNewsDesc, tvNewsDate, tvNewsRead;
    String newsTitle = null, newsImage = null, newsTime = null, newsContent = null;
    String imageLink = null;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    ArrayList<HashMap<String, String>> list;
    String team1Id = null, team2Id = null, team1 = null, team2 = null, team1Pts = null, team2Pts = null, venue = "--", time = null, matchTime = null;
    String teamlive1= null,teamlive2= null;
    ListView lvTeams;
    RelativeLayout ll2, ll3,ll4,ll5,rlsponsers;
    FrameLayout ll1,llLiveUpdatescore;
    ProgressDialog progressDialog;
    Activity activity;
    RelativeLayout flReview;
    ImageView ivReview;
    LinearLayout llSeasonReview;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvsignUp;
    Button bsignUp,addtocalender;
    private Handler handler;
    private  Runnable runnable;
    HashMap<String, String> single;
    String tagMain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) this.getActivity()).tvOrImage(false, "");

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(false).resetViewBeforeLoading(true).build();

        activity = getActivity();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initilizeViews();


        return view;
    }

    @Override
    public void onRefresh() {
        if (InternetOperations.checkIsOnlineViaIP()) {
            getHomeContentData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(activity, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(activity,"Refreshed!",Toast.LENGTH_SHORT).show();
    }

    public void initilizeViews() {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        ll1 = (FrameLayout) view.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) view.findViewById(R.id.ll2);
        ll3 = (RelativeLayout) view.findViewById(R.id.ll3);
        ll4 = (RelativeLayout) view.findViewById(R.id.ll4);
        ll5 =(RelativeLayout) view.findViewById(R.id.ll5);
        rlsponsers =(RelativeLayout) view.findViewById(R.id.rlsponsers);

        //lljpptv = (LinearLayout) view.findViewById(R.id.lljpptv);
        llsignUp = (LinearLayout) view.findViewById(R.id.llsignUp);
        llLiveUpdatescore = (FrameLayout) view.findViewById(R.id.llLiveUpdatescore);

        flReview = (RelativeLayout) view.findViewById(R.id.flReview);

        list = new ArrayList<HashMap<String, String>>();
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);
        llPoints = (LinearLayout) view.findViewById(R.id.llPoints);
        ivHomeMain = (ImageView) view.findViewById(R.id.ivHomeMain);
        addtocalender= (Button) view.findViewById(R.id.bAddToCalendar);
        addtocalender.setTypeface(CustomFonts.getRegularFont(activity));

       // ivReview = (ImageView) view.findViewById(R.id.ivReview);

        String imageUri = "drawable://" + R.drawable.schedule_back;
        imageLoader.displayImage(imageUri, ivHomeMain, options);
        //imageLoader.displayImage(imageUri, ivReview, options);


        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);

        llLatestUpdate = (LinearLayout) view.findViewById(R.id.llLatestUpdate);
        TextView tvLatest = (TextView) llLatestUpdate.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(activity));
        tvLatest.setText("NEXT MATCH");

        llLiveupdate = (LinearLayout) view.findViewById(R.id.llLiveUpdate);
        TextView tvmatchupdate = (TextView) llLiveupdate.findViewById(R.id.tvCrossHeader);
        tvmatchupdate.setTypeface(CustomFonts.getRegularFont(activity));
        tvmatchupdate.setText("MATCH UPDATE");

        llNews = (LinearLayout) view.findViewById(R.id.llNews);
        TextView tvNews = (TextView) llNews.findViewById(R.id.tvCrossHeader);
        tvNews.setTypeface(CustomFonts.getRegularFont(activity));
        tvNews.setText("NEWS");

        llTable = (LinearLayout) view.findViewById(R.id.llTable);
        TextView tvTable = (TextView) llTable.findViewById(R.id.tvCrossHeader);
        tvTable.setTypeface(CustomFonts.getRegularFont(activity));
        tvTable.setText("POINTS TABLE");

        llSeasonReview = (LinearLayout) view.findViewById(R.id.llReview);
        TextView tvReview = (TextView) llSeasonReview.findViewById(R.id.tvCrossHeader);
        tvReview.setTypeface(CustomFonts.getRegularFont(activity));
        tvReview.setText("SEASON 3 REVIEW");

        tvsignUp=(TextView)view.findViewById(R.id.tvsignUp);
        tvsignUp.setTypeface(CustomFonts.getBoldFont(activity));

        bsignUp=(Button)view.findViewById(R.id.bsignUp);
        bsignUp.setTypeface(CustomFonts.getBoldFont(activity));

        tvNo.setTypeface(CustomFonts.getRegularFont(activity));
        tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
        tvP.setTypeface(CustomFonts.getRegularFont(activity));
        tvW.setTypeface(CustomFonts.getRegularFont(activity));
        tvL.setTypeface(CustomFonts.getRegularFont(activity));
        tvPts.setTypeface(CustomFonts.getRegularFont(activity));

        ivT1 = (ImageView) view.findViewById(R.id.llivT1);
        ivT2 = (ImageView) view.findViewById(R.id.llivT2);


        llivT1live = (ImageView) view.findViewById(R.id.llivT1live);
        llivT2live = (ImageView) view.findViewById(R.id.llivT2live);

        hftime = (TextView) view.findViewById(R.id.halftime_fulltime);
        hftime.setTypeface(CustomFonts.getRegularFont(activity));

        tvlivescore1 = (TextView) view.findViewById(R.id.tvlivescore1);
        tvlivescore2 = (TextView) view.findViewById(R.id.tvlivescore2);

        tvlivescore1.setTypeface(CustomFonts.getScoreFont(activity));
        tvlivescore2.setTypeface(CustomFonts.getScoreFont(activity));

//        tvS1 = (TextView) view.findViewById(R.id.tvS1);
//        tvS2 = (TextView) view.findViewById(R.id.tvS2);
//        tvCo = (TextView) view.findViewById(R.id.tvCo);
//        tvS1.setTypeface(CustomFonts.getBoldFont(activity));
//        tvS2.setTypeface(CustomFonts.getBoldFont(activity));
//        tvCo.setTypeface(CustomFonts.getBoldFont(activity));


        tvMatchTime = (TextView) view.findViewById(R.id.lltvMatchTime);
        tvVenue = (TextView) view.findViewById(R.id.lltvVenue);
        //tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvMatchTime.setTypeface(CustomFonts.getLightFont(activity));
        tvVenue.setTypeface(CustomFonts.getLightFont(activity));
        //tvTime.setTypeface(CustomFonts.getLightFont(activity));

        tvmonth= (TextView) view.findViewById(R.id.tvmonth);
        tvmonth.setTypeface(CustomFonts.getRegularFont(activity));
        tvdays= (TextView) view.findViewById(R.id.tvdays);
        tvdays.setTypeface(CustomFonts.getRegularFont(activity));
        tvhours= (TextView) view.findViewById(R.id.tvhours);
        tvhours.setTypeface(CustomFonts.getRegularFont(activity));
        tvmins= (TextView) view.findViewById(R.id.tvmins);
        tvmins.setTypeface(CustomFonts.getRegularFont(activity));



        tvNewsHead = (TextView) view.findViewById(R.id.tvNewsHead);
        tvNewsDesc = (TextView) view.findViewById(R.id.tvNewsDesc);
        tvNewsDate = (TextView) view.findViewById(R.id.tvNewsDate);
        tvNewsRead = (TextView) view.findViewById(R.id.tvNewsRead);
        ivNews = (ImageView) view.findViewById(R.id.ivNews);

        tvNewsHead.setTypeface(CustomFonts.getBoldFont(activity));
        tvNewsDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvNewsDate.setTypeface(CustomFonts.getLightFont(activity));
        tvNewsRead.setTypeface(CustomFonts.getLightFont(activity));


       // mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        //viewPagerAdapter = new ViewPagerAdapter();

        //mViewPager.setAdapter(viewPagerAdapter);



        if (InternetOperations.checkIsOnlineViaIP()) {

            getHomeContentData();

        } else {
            progressDialog.dismiss();
            Toast.makeText(activity, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean a = false, b = false, c = false, d = false, e = false, f = false, jpptv = false, signUp = false,livescore=false,sponsers=false;

    public void getHomeContentData() {

        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONObject jsonObject = null;


                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getHomeContent");

                    jsonObject = new JSONObject(response);

                        //live update
                    try {
                        JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestMatch"));
//                        response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getLatestMatch");
//
//                        latestUpdate = new JSONObject(response);
                        Log.d("res data", latestUpdate.toString());

                        teamlive1 = latestUpdate.optString("team1");
                        teamlive2 = latestUpdate.optString("team2");
//                        tvS1+=1;
//                        tvS2+=2;
//                        team1Pts= String.valueOf(tvS1);
//                        team2Pts= String.valueOf(tvS2);
//                        team1Id="3";
//                        team2Id="4";
                        team1Pts = latestUpdate.optString("score1");
                        team2Pts = latestUpdate.optString("score2");
                        venue = latestUpdate.optString("stadium");
                        time = latestUpdate.optString("starttimedate");
                        team1Id = latestUpdate.optString("team1id");
                        team2Id = latestUpdate.optString("team2id");
                        matchTime = latestUpdate.optString("totalmatchtime");

                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //llLiveupdate.setVisibility(View.GONE);
                        livescore = true;
                    }
                    try {
                        JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestMatch"));
//                        response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getLatestMatch");
//                        latestUpdate = new JSONObject(response);
                        Log.d("res data", latestUpdate.toString());
                        team1 = latestUpdate.optString("team1");
                        team2 = latestUpdate.optString("team2");
                        venue = latestUpdate.optString("stadium");
                       time = latestUpdate.optString("starttimedate");
                      /*team1Id="3";
                        team2Id="4";*/
                       team1Id = latestUpdate.optString("team1id");
                       team2Id = latestUpdate.optString("team2id");
                       //addMatch( venue, time, team1Id, team2Id);
                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //ll1.setVisibility(View.GONE);
                        a = true;
                    }
                try {
                        JSONObject latestNews = new JSONObject(jsonObject.optString("news"));
                        newsTitle = latestNews.optString("name");
                        newsImage = latestNews.optString("image");
                        newsTime = latestNews.optString("timestamp");
                        newsContent = latestNews.optString("content");
                        imageLink = InternetOperations.SERVER_UPLOADS_URL + newsImage;
                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        b = true;
                    }

                    try {
                        String jObjectString = jsonObject.optString("points");
                        JSONArray jsonArray = new JSONArray(jObjectString);

                        if (jsonArray.length() != 0) {

                            if (list.size() > 0) { //need to clear the list if pull to refresh is initiated
                                list.clear();
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectPts = jsonArray.getJSONObject(i);
                                String id = String.valueOf(i + 1);
                                String name = jsonObjectPts.optString("name");
                                String p = jsonObjectPts.optString("played");
                                String w = jsonObjectPts.optString("wins");
                                String l = jsonObjectPts.optString("lost");
                                String points = jsonObjectPts.optString("point");
                                populate(id, name, p, w, l, points);
                            }
                        }
                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        c = true;
                    }

                    /*try {
                        String jObjectString = jsonObject.optString("review");
                        JSONArray jsonArray = new JSONArray(jObjectString);

                        if (jsonArray.length() != 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonReview = jsonArray.getJSONObject(i);
                                String t1 = jsonReview.optString("team1");
                                String t2 = jsonReview.optString("team2");
                                String t1Pts = jsonReview.optString("score1");
                                String t2Pts = jsonReview.optString("score2");
                                String ven = jsonReview.optString("stadium");
                                String t1Id = jsonReview.optString("team1id");
                                String t2Id = jsonReview.optString("team2id");
                                String galleryId = jsonReview.optString("galleryid");
                                String galleryName = jsonReview.optString("galleryname");

                                boolean last = false;

                                if (i == jsonArray.length()-1) {
                                    last = true;
                                }

                                viewPagerAdapter.addView(addMatch(i, t1Id, t2Id, t1Pts, t2Pts, ven, galleryId, galleryName, last));
                            }
                        }

                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //ll1.setVisibility(View.GONE);
                        d = true;
                    }*/

                    done = true;

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                    a = b = c = true;
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e) {
                    Log.e("JPP", Log.getStackTraceString(e));
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                if (done) {
                    refresh();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(activity, "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }.execute(null, null, null);
    }


    public void refresh() {
        f=true;
        e=true;
        jpptv = true;
        signUp = true;
        sponsers=true;


        if (!a) {
            ll1.setVisibility(View.VISIBLE);
        }

        if (!livescore)
        {
            llLiveUpdatescore.setVisibility(View.VISIBLE);

        }
        if (!b) {
            ll2.setVisibility(View.VISIBLE);
        }
        if (!c) {
            ll3.setVisibility(View.VISIBLE);
        }
        if (!d) {
            flReview.setVisibility(View.GONE);
        }
        if (!e) {
            ll4.setVisibility(View.VISIBLE);
        }
    /*    if (!f) {
            ll5.setVisibility(View.VISIBLE);
        }*/
        if (jpptv) {
            ll5.setVisibility(View.VISIBLE);
        }
        if (signUp) {
            llsignUp.setVisibility(View.VISIBLE);
        }
        if (sponsers) {
            rlsponsers.setVisibility(View.VISIBLE);
        }

        llPoints.removeAllViews();

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");


            if(team1Pts.equals("") && team2Pts.equals("")  )
            {
                ll1.setVisibility(View.VISIBLE);
                llLiveUpdatescore.setVisibility(View.GONE);
                tvlivescore1.setText("00");
                tvlivescore2.setText("00");

            }else
            {
                Log.d("hi ","color");
                scoreColour(team1Id,1);
                scoreColour(team2Id,2);
                tvlivescore1.setText(String.valueOf(team1Pts));
                tvlivescore2.setText(String.valueOf(team2Pts));
                llLiveUpdatescore.setVisibility(View.VISIBLE);
                ll1.setVisibility(View.GONE);
            }



//full time and half time
        hftime.setText(matchTime);

        tvVenue.setText(venue);


        if (time != null)
            tvMatchTime.setText(time + "(IST)");


         handler = new Handler();
         runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {

                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");

                        Date date = format.parse(time);
                        Calendar c = Calendar.getInstance();
                        Date today=c.getTime();
                    if (!today.after(date)) {

                        long difftime = date.getTime() - today.getTime();
                        long diffSeconds = difftime / 1000 % 60;
                        long diffMinutes = difftime / (60 * 1000) % 60;
                        if (diffSeconds > 0) {
                            diffMinutes = diffMinutes + 1;
                        }
                        long diffHours = difftime / (60 * 60 * 1000) % 24;
                        long diffDays=difftime/(60 * 60 * 1000)/24;
                        long diffmonth = (difftime / (24 * 60 * 60 * 1000)) / 30;

                        long cday = today.getDate();
                        long matchday = date.getDate();
                        long monthbefore = date.getMonth() - 1;
                       /* if (cday > matchday) {
                            if (monthbefore == 0 || monthbefore == 2 || monthbefore == 4 || monthbefore == 6 || monthbefore == 7 || monthbefore == 9 || monthbefore == 11) {
                                diffDays = 31 - (cday - matchday);
                            } else {
                                if (monthbefore == 3 || monthbefore == 5 || monthbefore == 8 || monthbefore == 10)
                                    diffDays = 30 - (cday - matchday);
                                else
                                    diffDays = 28 - (cday - matchday);
                            }
                        } else {
                            diffDays = matchday - cday;
                        }*/


                        Log.d(String.valueOf(matchday), String.valueOf(cday));


                        tvmins.setText(String.format("%02d", diffMinutes));
                        tvhours.setText(String.format("%02d", diffHours));
                        tvdays.setText(String.format("%02d", diffDays));
                        tvmonth.setText(String.format("%02d", diffmonth));

                        if(team1Pts.equals("") && team2Pts.equals("")  )
                        {
                            ll1.setVisibility(View.VISIBLE);
                            llLiveUpdatescore.setVisibility(View.GONE);
                            tvlivescore1.setText("00");
                            tvlivescore2.setText("00");

                        }

                    }else{
                        tvmins.setText(String.valueOf("00"));
                        tvhours.setText(String.valueOf("00"));
                        tvdays.setText(String.valueOf("00"));
                        tvmonth.setText(String.valueOf("00"));
                        handler.removeCallbacks(runnable);
                        //score view

                        if(team1Pts.equals("") && team2Pts.equals("")  )
                        {
                            tvlivescore1.setText("00");
                            tvlivescore2.setText("00");
                        }
                            Log.d("hi ","color");
                            scoreColour(team1,1);
                            scoreColour(team2,2);
                            tvlivescore1.setText(String.valueOf(team1Pts));
                            tvlivescore2.setText(String.valueOf(team2Pts));
                            llLiveUpdatescore.setVisibility(View.VISIBLE);
                            ll1.setVisibility(View.GONE);


                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                }
            };
            handler.postDelayed(runnable, 0);

        tagMain = team1 + "#" + team2 + "#" + time;

                addtocalender.setTag(tagMain);



        tvNewsHead.setText(newsTitle);

        if (!newsContent.startsWith("http"))
            tvNewsDesc.setText(newsContent);
        tvNewsDate.setText(newsTime);

        if (team1Id != null && team2Id != null) {
            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);
            imageLoader.displayImage(imageUriTeam1, ivT1, options);
            imageLoader.displayImage(imageUriTeam2, ivT2, options);
        }

        if (team1Id != null && team2Id != null) {
            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);
            imageLoader.displayImage(imageUriTeam1, llivT1live, options);
            imageLoader.displayImage(imageUriTeam2, llivT2live, options);
        }



        String imageUri = "drawable://" + R.drawable.schedule_back;
        imageLoader.displayImage(imageUri, ivHomeMain, options);

        imageLoader.displayImage(imageLink, ivNews, options);

        if (list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                LayoutInflater inflator = activity.getLayoutInflater();
                View viewPointsRow = inflator.inflate(R.layout.layout_points_row, null, false);

                TextView tvNo = (TextView) viewPointsRow.findViewById(R.id.tvNo); //find the different Views
                TextView tvTeam = (TextView) viewPointsRow.findViewById(R.id.tvTeam);
                TextView tvP = (TextView) viewPointsRow.findViewById(R.id.tvP);
                TextView tvW = (TextView) viewPointsRow.findViewById(R.id.tvW);
                TextView tvL = (TextView) viewPointsRow.findViewById(R.id.tvL);
                TextView tvPts = (TextView) viewPointsRow.findViewById(R.id.tvPts);

                LinearLayout llFull = (LinearLayout) viewPointsRow.findViewById(R.id.llFull);

                HashMap<String, String> map = list.get(i);

                String no = map.get("tvNo");
                String team = map.get("tvTeam");
                String p = map.get("tvP");
                String w = map.get("tvW");
                String l = map.get("tvL");
                String pts = map.get("tvPts");

                tvNo.setText(no);
                tvTeam.setText(team);

                if (team.equals("Jaipur Pink Panthers")) {
                    /*tvNo.setTypeface(CustomFonts.getRegularFont(activity));
                    tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
                    tvP.setTypeface(CustomFonts.getRegularFont(activity));
                    tvW.setTypeface(CustomFonts.getRegularFont(activity));
                    tvL.setTypeface(CustomFonts.getRegularFont(activity));
                    tvPts.setTypeface(CustomFonts.getRegularFont(activity));*/

                    tvNo.setTypeface(CustomFonts.getRegularFont(activity));
                    tvNo.setTextColor(Color.parseColor("#ee4a9b"));
                    tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
                    tvTeam.setTextColor(Color.parseColor("#ee4a9b"));
                    tvP.setTypeface(CustomFonts.getRegularFont(activity));
                    tvP.setTextColor(Color.parseColor("#ee4a9b"));
                    tvW.setTypeface(CustomFonts.getRegularFont(activity));
                    tvW.setTextColor(Color.parseColor("#ee4a9b"));
                    tvL.setTypeface(CustomFonts.getRegularFont(activity));
                    tvL.setTextColor(Color.parseColor("#ee4a9b"));
                    tvPts.setTypeface(CustomFonts.getRegularFont(activity));
                    tvPts.setTextColor(Color.parseColor("#ee4a9b"));
                    llFull.setBackgroundColor(Color.parseColor("#7bd9fa"));


                } else {
                    tvNo.setTypeface(CustomFonts.getLightFont(activity));
                    tvNo.setTextColor(Color.parseColor("black"));
                    tvTeam.setTypeface(CustomFonts.getLightFont(activity));
                    tvTeam.setTextColor(Color.parseColor("black"));
                    tvP.setTypeface(CustomFonts.getLightFont(activity));
                    tvP.setTextColor(Color.parseColor("black"));
                    tvW.setTypeface(CustomFonts.getLightFont(activity));
                    tvW.setTextColor(Color.parseColor("black"));
                    tvL.setTypeface(CustomFonts.getLightFont(activity));
                    tvL.setTextColor(Color.parseColor("black"));
                    tvPts.setTypeface(CustomFonts.getLightFont(activity));
                    tvPts.setTextColor(Color.parseColor("black"));

                    llFull.setBackgroundColor(Color.parseColor("#7bd9fa"));

                }
                tvP.setText(p);
                tvW.setText(w);
                tvL.setText(l);
                tvPts.setText(pts);

                llPoints.addView(viewPointsRow);
            }

        } else {

        }

        //viewPagerAdapter.notifyDataSetChanged();
    }

    public void populate(String n, String team, String p, String w, String l, String pts) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tvNo", n);
        map.put("tvTeam", team);
        map.put("tvP", p);
        map.put("tvW", w);
        map.put("tvL", l);
        map.put("tvPts", pts);
        list.add(map);
    }


    //set colour for score textview
    public void scoreColour(String team,int teamNo)
    {
        Log.d("hi ","color ");
        if(teamNo==1){
            if(team.equals("Patna Pirates"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#0a4436"));
            }
            else if(team.equals("Bengaluru Bulls"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#b01d21"));
            }
            else if(team.equals("Bengal Warriors"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#f26724"));
            }
            else if(team.equals("Dabang Delhi"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#d91f2d"));
            }
            else if(team.equals("Jaipur Pink Panthers"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#ee4a9b"));
                Log.d("hi pink panthers","color ");
            }
            else if(team.equals("Puneri Paltan"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#f04e23"));
            }
            else if(team.equals("Telugu Titans"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#da2131"));
            }
            else if(team.equals("U Mumba"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#f15922"));
                Log.d("hi u mumba","color ");
            }}

        else{
            if(team.equals("Patna Pirates"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#0a4436"));
            }
            else if(team.equals("Bengaluru Bulls"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#b01d21"));
            }
            else if(team.equals("Bengal Warriors"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#f26724"));
            }
            else if(team.equals("Dabang Delhi"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#d91f2d"));
            }
            else if(team.equals("Jaipur Pink Panthers"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#ee4a9b"));
                Log.d("hi pink panthers","color ");
            }
            else if(team.equals("Puneri Paltan"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#f04e23"));
            }
            else if(team.equals("Telugu Titans"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#da2131"));
            }
            else if(team.equals("U Mumba"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#f15922"));
                Log.d("hi u mumba","color ");
            }
        }
    }

    public int getTeamDrawable(String id) {

        int teamId = Integer.parseInt(id);
        TypedArray teamLogos = activity.getResources().obtainTypedArray(R.array.teamLogo);

        return teamLogos.getResourceId(teamId - 1, -1);
    }

    /*private void addMatch(String venue,String time,String team1Id,String team2Id)
    {

        ivT1.setImageResource(getTeamDrawable(team1Id));
        ivT2.setImageResource(getTeamDrawable(team2Id));
        tvMatchTime.setText(time + "(IST)");
        tvVenue.setText(venue);
    }*/

/*

    private LinearLayout addMatch(final int id, String t1Id, String t2Id, String t1Pts, String t2Pts, String ven, String galleryId, String galleryName, boolean last) {

        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.layout_review, null);

        ImageView ivT1R, ivT2R, ivLeft, ivRight;
        TextView tvS1R, tvS2R, tvCoR, tvVenueR, tvGoToGallery;

        ivT1R = (ImageView) v.findViewById(R.id.ivT1R);
        ivT2R = (ImageView) v.findViewById(R.id.ivT2R);

        ivLeft = (ImageView) v.findViewById(R.id.ivLeft);
        ivRight = (ImageView) v.findViewById(R.id.ivRight);

        tvS1R = (TextView) v.findViewById(R.id.tvS1);
        tvS2R = (TextView) v.findViewById(R.id.tvS2);
        tvCoR = (TextView) v.findViewById(R.id.tvCo);
        tvS1R.setTypeface(CustomFonts.getBoldFont(activity));
        tvS2R.setTypeface(CustomFonts.getBoldFont(activity));
        tvCoR.setTypeface(CustomFonts.getBoldFont(activity));

        tvVenueR = (TextView) v.findViewById(R.id.tvVenue);
        tvGoToGallery = (TextView) v.findViewById(R.id.tvGoToGallery);
        tvVenueR.setTypeface(CustomFonts.getLightFont(activity));
        tvGoToGallery.setTypeface(CustomFonts.getLightFont(activity));

        String imageUriTeam1 = "drawable://" + getTeamDrawable(t1Id);
        String imageUriTeam2 = "drawable://" + getTeamDrawable(t2Id);

        //String imageUriTeam1 = "drawable://" + R.drawable.t1;
        //String imageUriTeam2 = "drawable://" + R.drawable.t2;

        String imageUri = "drawable://" + R.drawable.schedule_back;


        //String imageUriTeam1 = "drawable://" + teamUrl[Integer.parseInt(t1Id)-1];
        //String imageUriTeam2 = "drawable://" + teamUrl[Integer.parseInt(t2Id)-1];
        String imageUriTeam3 = "drawable://" + R.drawable.t1;

        //Picasso.with(getActivity()).load(R.drawable.t11).into(ivT1R);
        //Picasso.with(getActivity()).load(R.drawable.t1).into(ivT2R);

       */
/* if (id == 0) {
            ivLeft.setImageResource(R.drawable.ic_blank);
        } else {
            ivLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchPrev();
                }
            });

        }

        if (last) {
            ivRight.setImageResource(R.drawable.ic_blank);
        } else {
            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchNext();
                }
            });
        }
*//*

        //ivT1R.setImageResource(getTeamDrawable(t1Id));
        //ivT2R.setImageResource(getTeamDrawable(t2Id));
        //ivT1R.setImageURI(Uri.parse(imageUriTeam1));
        //ivT2R.setImageURI(Uri.parse(imageUriTeam2));

        ivT1R.setImageResource(getTeamDrawable(t1Id));
        ivT2R.setImageResource(getTeamDrawable(t2Id));

        String jay = "assets://";
        //Log.d(TAG, "URI = " + imageUri + fileName);
        //imageLoader.displayImage(jay+"t1.png", ivT1R);
        //ImageLoader.getInstance().displayImage(imageUriTeam1, ivT1R);
        //ImageLoader.getInstance().displayImage(imageUriTeam2, ivT2R);

        */
/*imageLoader.displayImage("drawable://" + R.drawable.ic_bottom_gallery_white, ivT1R);
        imageLoader.displayImage("drawable://" + R.drawable.ic_bottom_gallery_white, ivT2R, options);
        ivT1R.setBackground(R.drawable.ic_bottom_gallery_white);*//*


        */
/*imageLoader.displayImage("http://jaipurpinkpanthers.com/admin/uploads/736x327-14.jpg",ivT1R,options);
        imageLoader.displayImage("http://jaipurpinkpanthers.com/admin/uploads/736x327-14.jpg",ivT2R,options);*//*


        tvS1R.setText(t1Pts);
        tvS2R.setText(t2Pts);

        tvVenueR.setText(ven);

        String tag = galleryId + "#" + galleryName;
        tvGoToGallery.setTag(tag);

        return v;
    }
*/

  /*  private void matchPrev() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    private void matchNext() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }*/

    private static final int[] teamUrl = new int[] {
            R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8
    };

}
