package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.adapters.PointsAdapter;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;
import com.wohlig.jaipurpinkpanthers.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PointsFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    ListView lvTeams;
    ArrayList<HashMap<String,String>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_points, container, false);

        initilizeViews();

        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                json();
            }
        }, 1000);*/

        return view;
    }

    public void initilizeViews() {

        list=new ArrayList<HashMap<String, String>>();

        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);

        tvNo.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvP.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvW.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvL.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvPts.setTypeface(CustomFonts.getRegularFont(getActivity()));

        getData();

        populate("1", "Jaipur Pink Panthers", "5", "4", "1", "8");
        populate("2", "Telugu Titans", "2", "1", "1", "2");
        populate("3", "Bengaluru Bulls", "4", "3","1","6");
        populate("4", "Puneri Paltan", "6", "2","5","2");
        populate("5", "Dabang Delhi","5","0","5","0");
        //populate();
        Log.e("JPP listSize", String.valueOf(list.size()));
        if(list.size()>0) {
            PointsAdapter pointsAdapter = new PointsAdapter(getActivity(), list);
            lvTeams.setAdapter(pointsAdapter);
        }
        else{
            //istView.setEmptyView(tvNoBets);
        }
    }

    /*public void populate(){
        for(int i=1; i <6 ; i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("tvNo",String.valueOf(i));
            map.put("tvTeam","Jaipur Pink Panthers");
            map.put("tvP","5");
            map.put("tvW","4");
            map.put("tvL","1");
            map.put("tvPts","8");
            list.add(map);
        }
    }*/

    public void getData(){

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }

                try {
                    String response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getallpoint");

                    //JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);

                    Log.e("JPP Obj", jsonObject.optString("queryresult"));

                    JSONArray jsonArray = new JSONArray(jsonObject.optString("queryresult"));

                    Log.e("JPP Arr Len", String.valueOf(jsonArray.length()));

                }catch(IOException io){
                    io.printStackTrace();
                    Log.e("JPP", io.getStackTrace().toString());
                    Log.e("JPP", Log.getStackTraceString(io));
                }catch(JSONException je){
                    Log.e("JPP", "JE");
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

            }
        }.execute(null, null, null);

    }

    public void populate(String n, String team, String p, String w, String l, String pts){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("tvNo",n);
            map.put("tvTeam",team);
            map.put("tvP",p);
            map.put("tvW",w);
            map.put("tvL",l);
            map.put("tvPts",pts);
            list.add(map);
    }
}
