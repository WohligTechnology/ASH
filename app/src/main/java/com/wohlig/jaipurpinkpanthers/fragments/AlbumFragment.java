package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.wohlig.jaipurpinkpanthers.MainActivity;
import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.adapters.AlbumAdapter;
import com.wohlig.jaipurpinkpanthers.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AlbumFragment extends Fragment {

    View view;
    private int id;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Activity activity;
    public GridView gvImages;
    ArrayList<HashMap<String, String>> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_album, container, false);

        id = ((MainActivity) this.getActivity()).getGalleryId();

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

    public void initilizeViews(){

        list = new ArrayList<HashMap<String, String>>();
        gvImages = (GridView) view.findViewById(R.id.gvImages);
        getAlbumData();
    }

    public void getAlbumData(){

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                //JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                //http://192.168.0.124/jppbackend/index.php/json/getGallerySlide?galleryid=2
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getGallerySlide?galleryid="+id);

                    jsonArray = new JSONArray(response);
                    Log.e("JPP objjjj", jsonArray.toString());

                    for (int j = 0; j < jsonArray.length(); j++) {
                        /*
                        "id": "1",
                        "order": "1",
                        "gallery": "2",
                        "name": "Jaipur Pink Panther V/S Patna Pirates 1",
                        "image": "g4.jpg"
                        */
                        String id = null, name = null, image = null, timestamp = null, content = null;

                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        id = jsonObject.optString("id");
                        name = jsonObject.optString("name");
                        image = jsonObject.optString("image");

                        populate(id, name, image);
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
            AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), list);
            gvImages.setAdapter(albumAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String id, String name, String image) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("title", name);
        map.put("image", image);
        list.add(map);
    }

}
