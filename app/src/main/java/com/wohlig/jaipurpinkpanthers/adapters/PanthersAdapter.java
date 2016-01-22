package com.wohlig.jaipurpinkpanthers.adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jay on 22-01-2016.
 */
public class PanthersAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TypedArray playerImages;

    public PanthersAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;

        playerImages = activity.getResources().obtainTypedArray(R.array.playerImages);
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
        ImageView player_image;
        TextView player_name, player_type;
        LinearLayout llPlayer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_player, null); //change the name of the layout

            holder.player_image = (ImageView) convertView.findViewById(R.id.player_image); //find the different Views
            holder.player_name = (TextView) convertView.findViewById(R.id.player_name);
            holder.player_type = (TextView) convertView.findViewById(R.id.player_type);
            //holder.llPlayer = (LinearLayout) convertView.findViewById(R.id.llPlayer);

            holder.player_name.setTypeface(CustomFonts.getProfileFont(activity));
            holder.player_type.setTypeface(CustomFonts.getProfileFont(activity));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String playerInfo = map.get("PlayerInfo");
        List<String> playerInfoList = Arrays.asList(playerInfo.split("#"));

        String playerId = playerInfoList.get(0);            //playerId
        String playerName = playerInfoList.get(1);          //playerName
        String playerType = playerInfoList.get(2);          //playerType

        //set the hash maps
        //holder.llPlayer.setTag(playerId);
        holder.player_image.setImageResource(playerImages.getResourceId(position, 0));
        holder.player_name.setText(playerName.toUpperCase());
        holder.player_type.setText(playerType.toUpperCase());


        return convertView;
    }
}