package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.MatchUpdateAdapter;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchUpdateFragment extends Fragment {
    View view;
    ListView matchupdate;
    ArrayList<HashMap<String, String>> list;
    ProgressDialog progressDialog;
    AsyncTask asyncTask;
    //Handler handler = getActivity().getWindow().getDecorView().getHandler();
    private static Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_matchupdate, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

        list = new ArrayList<HashMap<String, String>>();
        matchupdate = (ListView) view.findViewById(R.id.lvmatchupdate);

        if (InternetOperations.checkIsOnlineViaIP()) {
            getNewsData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getNewsData() {

        asyncTask = new AsyncTask<Void, Void, String>() {
        boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getScheduleForIosAndroidSeason4");

                    jsonArray = new JSONArray(response);
                    Log.d("matchupdate", String.valueOf(jsonArray));
                    String header;
                    int i=jsonArray.length();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        String team1 = null, team2 = null, team1Pts = null, team2Pts = null, venue = null, time = null, team1Id = null, team2Id = null, matchTime = null;

                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        header = String.valueOf(i);
                        i--;
                        Log.d("matchupdate", String.valueOf(jsonObject));
                        team1 = jsonObject.optString("team1");
                        team2 = jsonObject.optString("team2");
                        team1Pts = jsonObject.optString("score1");
                        team2Pts = jsonObject.optString("score2");
                        venue = jsonObject.optString("stadium");
                        time = jsonObject.optString("starttimedate");
                        team1Id = jsonObject.optString("team1id");
                        team2Id = jsonObject.optString("team2id");
                        matchTime = jsonObject.optString("matchtime");

                        populate(team1, team2, team1Pts, team2Pts, venue, time, team1Id, team2Id, matchTime, header);
                    }
                    //refresh();

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e){
                    Log.e("JPP", Log.getStackTraceString(e));
                }

                return null;
            }

            protected void onPostExecute(String s) {
//                progressDialog.dismiss();
                done = true;
                if (done) {
                    progressDialog.dismiss();
                    refresh();
                } else {
                    Toast.makeText(getActivity(), "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);

    }

    public void refresh() {
        if (list.size() > 0) {
            MatchUpdateAdapter matchUpdateAdapter = new MatchUpdateAdapter(getActivity(), list);
            matchupdate.setAdapter(matchUpdateAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String team1, String team2 , String team1Pts, String team2Pts, String venue,String time,String team1Id,String team2Id,String matchTime,String header) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("team1", team1);
        map.put("team2", team2);
        map.put("score1", team1Pts);
        map.put("score2", team2Pts);
        map.put("stadium", venue);
        map.put("starttimedate", time);
        map.put("team1id", team1Id);
        map.put("team2id", team2Id);
        map.put("matchtime", matchTime);
        map.put("header", header);
        list.add(map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        asyncTask.cancel(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        asyncTask.cancel(true);
    }
}
