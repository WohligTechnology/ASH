package com.wohlig.jaipurpinkpanthers.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.util.CustomFonts;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jay on 25-01-2016.
 */
public class PointsAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;

    public PointsAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
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
        TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_points_row, null); //change the name of the layout

            holder.tvNo = (TextView) convertView.findViewById(R.id.tvNo); //find the different Views
            holder.tvTeam = (TextView) convertView.findViewById(R.id.tvTeam);
            holder.tvP = (TextView) convertView.findViewById(R.id.tvP);
            holder.tvW = (TextView) convertView.findViewById(R.id.tvW);
            holder.tvL = (TextView) convertView.findViewById(R.id.tvL);
            holder.tvPts = (TextView) convertView.findViewById(R.id.tvPts);

            holder.tvNo.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvTeam.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvP.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvW.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvL.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvPts.setTypeface(CustomFonts.getLightFont(activity));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String no = map.get("tvNo");
        String team = map.get("tvTeam");
        String p = map.get("tvP");
        String w = map.get("tvW");
        String l = map.get("tvL");
        String pts = map.get("tvPts");

        holder.tvNo.setText(no);
        holder.tvTeam.setText(team);
        holder.tvP.setText(p);
        holder.tvW.setText(w);
        holder.tvL.setText(l);
        holder.tvPts.setText(pts);

        return convertView;
    }
}