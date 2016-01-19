package com.wohlig.jaipurpinkpanthers.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;


public class TeamAdapter extends BaseAdapter {

    private Context mContext;
    public Activity mActivity;

    public TeamAdapter(Context c) { mContext = c; }

    public int getCount() { return 0; }

    public Object getItem(int position) { return null; }

    public long getItemId(int position) { return 0; }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = mActivity.getLayoutInflater();

        ImageView mPlayerImage;
        TextView mPlayerName, mPlayerType;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_player, null, false);
            mPlayerImage = (ImageView) convertView.findViewById(R.id.player_image);
            mPlayerName = (TextView) convertView.findViewById(R.id.player_name);
            mPlayerType = (TextView) convertView.findViewById(R.id.player_type);
        } else {
            //imageView = (ImageView) convertView;
        }

        //playerImage.setImageResource(teamImage[position]);
        return convertView;
    }
}