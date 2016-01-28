package com.wohlig.jaipurpinkpanthers.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jay on 27-01-2016.
 */
public class NewsAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public NewsAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

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
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        //all the fields in layout specified
        ImageView ivNewsImage;
        TextView tvNewsTitle, tvNewsDate, tvNewsDesc;
        LinearLayout llNewsClick;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_newsmain, null); //change the name of the layout

            holder.ivNewsImage = (ImageView) convertView.findViewById(R.id.ivNewsImage); //find the different Views
            holder.tvNewsTitle = (TextView) convertView.findViewById(R.id.tvNewsTitle);
            holder.tvNewsDate = (TextView) convertView.findViewById(R.id.tvNewsDate);
            holder.tvNewsDesc = (TextView) convertView.findViewById(R.id.tvNewsDesc);
            holder.llNewsClick = (LinearLayout) convertView.findViewById(R.id.llNewsClick);

            holder.tvNewsTitle.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvNewsDate.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvNewsDesc.setTypeface(CustomFonts.getLightFont(activity));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String id = map.get("id");
        String title = map.get("title");
        String date = map.get("date");
        String desc = map.get("desc");
        String image = map.get("image");

        image = InternetOperations.SERVER_UPLOADS_URL + image;
        holder.tvNewsTitle.setText(title);
        holder.tvNewsDate.setText(date);
        holder.tvNewsDesc.setText(desc);

        String tag = title+"#"+image+"#"+date+"#"+desc;
        holder.llNewsClick.setTag(tag);

        imageLoader.displayImage(image, holder.ivNewsImage, options);

        return convertView;
    }
}