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
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;
import com.wohlig.jaipurpinkpanthers.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HomeFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    LinearLayout llLatestUpdate, llNews, llTable;
    ImageView ivT1, ivT2;
    TextView tvS1, tvS2, tvCo, tvVenue, tvTime;
    TextView tvNewsHead, tvNewsDesc, tvNewsDate, tvNewsRead;
    JSONArray latestUpdate = null, news = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

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

        tvNewsHead.setTypeface(CustomFonts.getBoldFont(getActivity()));
        tvNewsDesc.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvNewsDate.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvNewsRead.setTypeface(CustomFonts.getLightFont(getActivity()));

        getHomeContentData();
    }

    String team1 = null, team2 = null, team1Pts = null, team2Pts = null, venue = null, time = null;

    public void getHomeContentData() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONObject jsonObject = null;
                //JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getHomeContent");

                    jsonObject = new JSONObject(response);

                    //jsonArray = new JSONArray(response);

                            /*"id": "1",
                            "stadium": "Wankhede Stadium, Mumbai",
                            "team1": "Jaipur Pink Panther",
                            "team2": "Patna Pirates",
                            "bookticket": "",
                            "timestamp": "2016-01-27 12:26:26",
                            "starttime": "03:05:00",
                            "score1": "23",
                            "score2": "34",
                            "startdate": "28th January 2016"*/

                    JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestupdate"));
                    //latestUpdate = jsonArray.getJSONObject(0);
                    team1 = latestUpdate.optString("team1");
                    team2 = latestUpdate.optString("team2");
                    team1Pts = latestUpdate.optString("score1");
                    team2Pts = latestUpdate.optString("score1");
                    venue = latestUpdate.optString("stadium");
                    time = latestUpdate.optString("starttimedate");

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
        tvTime.setText(time+"(IST)");
    }
}
