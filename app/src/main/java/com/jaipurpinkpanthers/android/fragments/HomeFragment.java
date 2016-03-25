package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    LinearLayout llLatestUpdate, llNews, llTable, llPoints;
    ImageView ivT1, ivT2, ivNews, ivHomeMain;
    TextView tvS1, tvS2, tvCo, tvVenue, tvTime, tvMatchTime;
    TextView tvNewsHead, tvNewsDesc, tvNewsDate, tvNewsRead;
    String newsTitle = null, newsImage = null, newsTime = null, newsContent = null;
    String imageLink = null;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    ArrayList<HashMap<String, String>> list;
    String team1Id = null, team2Id = null, team1 = null, team2 = null, team1Pts = "--", team2Pts = "--", venue = "--", time = null, matchTime = null;
    ListView lvTeams;
    RelativeLayout ll2, ll3;
    FrameLayout ll1;
    ProgressDialog progressDialog;
    Activity activity;
    RelativeLayout flReview;
    ImageView ivReview;
    LinearLayout llSeasonReview;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) this.getActivity()).tvOrImage(false, "");

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        activity = getActivity();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

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
            //swipeRefreshLayout.setRefreshing(false);
        }
        //Toast.makeText(activity,"Refreshed!",Toast.LENGTH_SHORT).show();
    }

    public void initilizeViews() {

        /*swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);*/

        ll1 = (FrameLayout) view.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) view.findViewById(R.id.ll2);
        ll3 = (RelativeLayout) view.findViewById(R.id.ll3);

        flReview = (RelativeLayout) view.findViewById(R.id.flReview);

        list = new ArrayList<HashMap<String, String>>();
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);
        llPoints = (LinearLayout) view.findViewById(R.id.llPoints);
        ivHomeMain = (ImageView) view.findViewById(R.id.ivHomeMain);

        ivReview = (ImageView) view.findViewById(R.id.ivReview);

        String imageUri = "drawable://" + R.drawable.schedule_back;
        imageLoader.displayImage(imageUri, ivHomeMain, options);
        imageLoader.displayImage(imageUri, ivReview, options);


        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);

        llLatestUpdate = (LinearLayout) view.findViewById(R.id.llLatestUpdate);
        TextView tvLatest = (TextView) llLatestUpdate.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(activity));
        tvLatest.setText("LATEST UPDATE");

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

        tvNo.setTypeface(CustomFonts.getRegularFont(activity));
        tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
        tvP.setTypeface(CustomFonts.getRegularFont(activity));
        tvW.setTypeface(CustomFonts.getRegularFont(activity));
        tvL.setTypeface(CustomFonts.getRegularFont(activity));
        tvPts.setTypeface(CustomFonts.getRegularFont(activity));

        ivT1 = (ImageView) view.findViewById(R.id.ivT1);
        ivT2 = (ImageView) view.findViewById(R.id.ivT2);

        tvS1 = (TextView) view.findViewById(R.id.tvS1);
        tvS2 = (TextView) view.findViewById(R.id.tvS2);
        tvCo = (TextView) view.findViewById(R.id.tvCo);
        tvS1.setTypeface(CustomFonts.getBoldFont(activity));
        tvS2.setTypeface(CustomFonts.getBoldFont(activity));
        tvCo.setTypeface(CustomFonts.getBoldFont(activity));


        tvMatchTime = (TextView) view.findViewById(R.id.tvMatchTime);
        tvVenue = (TextView) view.findViewById(R.id.tvVenue);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvMatchTime.setTypeface(CustomFonts.getLightFont(activity));
        tvVenue.setTypeface(CustomFonts.getLightFont(activity));
        tvTime.setTypeface(CustomFonts.getLightFont(activity));


        tvNewsHead = (TextView) view.findViewById(R.id.tvNewsHead);
        tvNewsDesc = (TextView) view.findViewById(R.id.tvNewsDesc);
        tvNewsDate = (TextView) view.findViewById(R.id.tvNewsDate);
        tvNewsRead = (TextView) view.findViewById(R.id.tvNewsRead);
        ivNews = (ImageView) view.findViewById(R.id.ivNews);

        tvNewsHead.setTypeface(CustomFonts.getBoldFont(activity));
        tvNewsDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvNewsDate.setTypeface(CustomFonts.getLightFont(activity));
        tvNewsRead.setTypeface(CustomFonts.getLightFont(activity));

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter();

        mViewPager.setAdapter(viewPagerAdapter);

        if (InternetOperations.checkIsOnlineViaIP()) {
            getHomeContentData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(activity, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean a = false, b = false, c = false, d = false;

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

                    try {
                        JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestupdate"));
                        team1 = latestUpdate.optString("team1");
                        team2 = latestUpdate.optString("team2");
                        team1Pts = latestUpdate.optString("score1");
                        team2Pts = latestUpdate.optString("score2");
                        venue = latestUpdate.optString("stadium");
                        time = latestUpdate.optString("starttimedate");
                        team1Id = latestUpdate.optString("team1id");
                        team2Id = latestUpdate.optString("team2id");
                        matchTime = latestUpdate.optString("matchtime");
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


                    try {
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
                    }

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
                    //swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(activity, "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                    //swipeRefreshLayout.setRefreshing(false);
                }
            }
        }.execute(null, null, null);
    }

    public void refresh() {

        /*if(!a){
            ll1.setVisibility(View.GONE);
        }*/

        if (!b) {
            ll2.setVisibility(View.VISIBLE);
        }
        if (!c) {
            ll3.setVisibility(View.VISIBLE);
        }
        if (!d) {
            flReview.setVisibility(View.VISIBLE);
        }

        llPoints.removeAllViews();

        tvS1.setText(team1Pts);
        tvS2.setText(team2Pts);
        tvVenue.setText(venue);
        tvMatchTime.setText(matchTime);

        if (time != null)
            tvTime.setText(time + "(IST)");

        tvNewsHead.setText(newsTitle);

        if (!newsContent.startsWith("http"))
            tvNewsDesc.setText(newsContent);
        tvNewsDate.setText(newsTime);

        if (team1Id != null || team2Id != null) {
            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);

            imageLoader.displayImage(imageUriTeam1, ivT1, options);
            imageLoader.displayImage(imageUriTeam2, ivT2, options);
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

                    tvNo.setTypeface(CustomFonts.getLightFont(activity));
                    tvTeam.setTypeface(CustomFonts.getLightFont(activity));
                    tvP.setTypeface(CustomFonts.getLightFont(activity));
                    tvW.setTypeface(CustomFonts.getLightFont(activity));
                    tvL.setTypeface(CustomFonts.getLightFont(activity));
                    tvPts.setTypeface(CustomFonts.getLightFont(activity));
                    llFull.setBackgroundColor(Color.parseColor("#4ECAF5"));
                } else {
                    tvNo.setTypeface(CustomFonts.getLightFont(activity));
                    tvTeam.setTypeface(CustomFonts.getLightFont(activity));
                    tvP.setTypeface(CustomFonts.getLightFont(activity));
                    tvW.setTypeface(CustomFonts.getLightFont(activity));
                    tvL.setTypeface(CustomFonts.getLightFont(activity));
                    tvPts.setTypeface(CustomFonts.getLightFont(activity));
                }


                tvP.setText(p);
                tvW.setText(w);
                tvL.setText(l);
                tvPts.setText(pts);

                llPoints.addView(viewPointsRow);
            }

        } else {

        }

        viewPagerAdapter.notifyDataSetChanged();
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

    public int getTeamDrawable(String id) {

        int teamId = Integer.parseInt(id);
        TypedArray teamLogos = activity.getResources().obtainTypedArray(R.array.teamLogo);

        return teamLogos.getResourceId(teamId - 1, -1);
    }

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

        /*String imageUriTeam1 = "drawable://" + getTeamDrawable(t1Id);
        String imageUriTeam2 = "drawable://" + getTeamDrawable(t2Id);*/

        /*String imageUriTeam1 = "drawable://" + R.drawable.t1;
        String imageUriTeam2 = "drawable://" + R.drawable.t2;*/

        String imageUri = "drawable://" + R.drawable.schedule_back;


        String imageUriTeam1 = "drawable://" + teamUrl[Integer.parseInt(t1Id)-1];
        String imageUriTeam2 = "drawable://" + teamUrl[Integer.parseInt(t2Id)-1];
        String imageUriTeam3 = "drawable://" + R.drawable.t1;

        /*Picasso.with(getActivity()).load(R.drawable.t11).into(ivT1R);
        Picasso.with(getActivity()).load(R.drawable.t1).into(ivT2R);*/

        if (id == 0) {
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

        /*imageLoader.displayImage("drawable://" + R.drawable.ic_bottom_gallery_white, ivT1R);
        imageLoader.displayImage("drawable://" + R.drawable.ic_bottom_gallery_white, ivT2R, options);
        ivT1R.setBackground(R.drawable.ic_bottom_gallery_white);*/

        /*imageLoader.displayImage("http://jaipurpinkpanthers.com/admin/uploads/736x327-14.jpg",ivT1R,options);
        imageLoader.displayImage("http://jaipurpinkpanthers.com/admin/uploads/736x327-14.jpg",ivT2R,options);*/

        tvS1R.setText(t1Pts);
        tvS2R.setText(t2Pts);

        tvVenueR.setText(ven);

        String tag = galleryId + "#" + galleryName;
        tvGoToGallery.setTag(tag);

        return v;
    }

    private void matchPrev() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    private void matchNext() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

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
