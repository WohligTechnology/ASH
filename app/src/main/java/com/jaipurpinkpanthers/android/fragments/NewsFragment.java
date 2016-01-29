package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.NewsAdapter;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NewsFragment extends Fragment {
    View view;
    ListView lvNews;
    ArrayList<HashMap<String, String>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

        list = new ArrayList<HashMap<String, String>>();
        lvNews = (ListView) view.findViewById(R.id.lvNews);

        if(InternetOperations.checkIsOnlineViaIP()){
            getNewsData();
        }else{
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getNewsData() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                //JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getallnews");

                    jsonArray = new JSONArray(response);
                    Log.e("JPP objjjj", jsonArray.toString());

                    for (int j = 0; j < jsonArray.length(); j++) {

                        /*"id": "1",
                            "name": "Lorem Ipsum",
                            "image": "n11.jpg",
                            "timestamp": "27 Jan 2016",
                            "content": "Lorem Ipsum is*/
                        String id = null, name = null, image = null, timestamp = null, content = null;

                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        id = jsonObject.optString("id");
                        name = jsonObject.optString("name");
                        image = jsonObject.optString("image");
                        timestamp = jsonObject.optString("timestamp");
                        content = jsonObject.optString("content");

                        populate(id, name, image, timestamp, content);
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

    public void refresh() {
        if (list.size() > 0) {
            NewsAdapter newsAdapter = new NewsAdapter(getActivity(), list);
            lvNews.setAdapter(newsAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String id, String name, String image, String timestamp, String content) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("title", name);
        map.put("date", timestamp);
        map.put("desc", content);
        map.put("image", image);
        list.add(map);
    }

}
