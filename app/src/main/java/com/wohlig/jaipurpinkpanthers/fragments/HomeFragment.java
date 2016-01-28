package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;
import com.wohlig.jaipurpinkpanthers.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    LinearLayout llLatestUpdate, llNews, llTable, llPoints;
    ImageView ivT1, ivT2, ivNews;
    TextView tvS1, tvS2, tvCo, tvVenue, tvTime;
    TextView tvNewsHead, tvNewsDesc, tvNewsDate, tvNewsRead;
    String newsTitle = null, newsImage = null, newsTime = null, newsContent = null;
    String imageLink = null;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    ArrayList<HashMap<String, String>> list;
    String team1 = null, team2 = null, team1Pts = "--", team2Pts = "--", venue = "--", time = null;
    ListView lvTeams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

        list = new ArrayList<HashMap<String, String>>();
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);
        llPoints = (LinearLayout) view.findViewById(R.id.llPoints);

        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);

        llLatestUpdate = (LinearLayout) view.findViewById(R.id.llLatestUpdate);
        TextView tvLatest = (TextView) llLatestUpdate.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvLatest.setText("LATEST UPDATE");

        llNews = (LinearLayout) view.findViewById(R.id.llNews);
        TextView tvNews = (TextView) llNews.findViewById(R.id.tvCrossHeader);
        tvNews.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvNews.setText("NEWS");

        llTable = (LinearLayout) view.findViewById(R.id.llTable);
        TextView tvTable = (TextView) llTable.findViewById(R.id.tvCrossHeader);
        tvTable.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTable.setText("TABLE");


        tvNo.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvP.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvW.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvL.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvPts.setTypeface(CustomFonts.getRegularFont(getActivity()));

        ivT1 = (ImageView) view.findViewById(R.id.ivT1);
        ivT2 = (ImageView) view.findViewById(R.id.ivT2);

        tvS1 = (TextView) view.findViewById(R.id.tvS1);
        tvS2 = (TextView) view.findViewById(R.id.tvS2);
        tvCo = (TextView) view.findViewById(R.id.tvCo);
        tvS1.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvS2.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvCo.setTypeface(CustomFonts.getBoldFont(getActivity()));

        tvVenue = (TextView) view.findViewById(R.id.tvVenue);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvVenue.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvTime.setTypeface(CustomFonts.getLightFont(getActivity()));

        tvNewsHead = (TextView) view.findViewById(R.id.tvNewsHead);
        tvNewsDesc = (TextView) view.findViewById(R.id.tvNewsDesc);
        tvNewsDate = (TextView) view.findViewById(R.id.tvNewsDate);
        tvNewsRead = (TextView) view.findViewById(R.id.tvNewsRead);
        ivNews = (ImageView) view.findViewById(R.id.ivNews);

        tvNewsHead.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvNewsDesc.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvNewsDate.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvNewsRead.setTypeface(CustomFonts.getLightFont(getActivity()));

        getHomeContentData();
    }


    public void getHomeContentData() {

        new AsyncTask<Void, Void, String>() {
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

                    JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestupdate"));
                    team1 = latestUpdate.optString("team1");
                    team2 = latestUpdate.optString("team2");
                    team1Pts = latestUpdate.optString("score1");
                    team2Pts = latestUpdate.optString("score1");
                    venue = latestUpdate.optString("stadium");
                    time = latestUpdate.optString("starttimedate");

                    JSONObject latestNews = new JSONObject(jsonObject.optString("news"));
                    newsTitle = latestNews.optString("name");
                    newsImage = latestNews.optString("image");
                    newsTime = latestNews.optString("timestamp");
                    newsContent = latestNews.optString("content");
                    imageLink = InternetOperations.SERVER_UPLOADS_URL + newsImage;

                    String jObjectString = jsonObject.optString("points");
                    JSONArray jsonArray = new JSONArray(jObjectString);

                    Log.e("JPP Arr Len", String.valueOf(jsonArray.length()));
                    Log.e("JPP Arr string", jsonArray.toString());

                    if (jsonArray.length() != 0) {

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


                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                refresh();
            }
        }.execute(null, null, null);
    }

    public void refresh(){
        tvS1.setText(team1Pts);
        tvS2.setText(team2Pts);
        tvVenue.setText(venue);

        if(time != null)
            tvTime.setText(time+"(IST)");

        tvNewsHead.setText(newsTitle);
        tvNewsDesc.setText(newsContent);
        tvNewsDate.setText(newsTime);

        imageLoader.displayImage(imageLink, ivNews, options);

        if (list.size() > 0) {

            for(int i = 0; i < list.size(); i++) {
                LayoutInflater inflator = getActivity().getLayoutInflater();
                View viewPointsRow = inflator.inflate(R.layout.layout_points_row, null, false);

                TextView tvNo = (TextView) viewPointsRow.findViewById(R.id.tvNo); //find the different Views
                TextView tvTeam = (TextView) viewPointsRow.findViewById(R.id.tvTeam);
                TextView tvP = (TextView) viewPointsRow.findViewById(R.id.tvP);
                TextView tvW = (TextView) viewPointsRow.findViewById(R.id.tvW);
                TextView tvL = (TextView) viewPointsRow.findViewById(R.id.tvL);
                TextView tvPts = (TextView) viewPointsRow.findViewById(R.id.tvPts);

                tvNo.setTypeface(CustomFonts.getLightFont(getActivity()));
                tvTeam.setTypeface(CustomFonts.getLightFont(getActivity()));
                tvP.setTypeface(CustomFonts.getLightFont(getActivity()));
                tvW.setTypeface(CustomFonts.getLightFont(getActivity()));
                tvL.setTypeface(CustomFonts.getLightFont(getActivity()));
                tvPts.setTypeface(CustomFonts.getLightFont(getActivity()));

                HashMap<String, String> map = list.get(i);

                String no = map.get("tvNo");
                String team = map.get("tvTeam");
                String p = map.get("tvP");
                String w = map.get("tvW");
                String l = map.get("tvL");
                String pts = map.get("tvPts");

                tvNo.setText(no);
                tvTeam.setText(team);
                tvP.setText(p);
                tvW.setText(w);
                tvL.setText(l);
                tvPts.setText(pts);

                llPoints.addView(viewPointsRow);
            }

        } else {
            //istView.setEmptyView(tvNoBets);
        }
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
}
