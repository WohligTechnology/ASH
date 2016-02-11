package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleFragment extends Fragment {
    View view;
    LinearLayout llUpcomingMatch;
    ImageView ivT1, ivT2, ivBanner;
    TextView tvVenue, tvTime, tvAddToCalendar;
    LinearLayout llOtherMatches;
    TextView tvBook;
    LinearLayout llAddToCalendar, llSchedule;
    ArrayList<HashMap<String, String>> list;
    HashMap<String, String> single;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    RelativeLayout rlSchedule1, rlSchedule2;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

        single = new HashMap<String, String>();
        list = new ArrayList<HashMap<String, String>>();

        rlSchedule1 = (RelativeLayout) view.findViewById(R.id.rlSchedule1);
        rlSchedule2 = (RelativeLayout) view.findViewById(R.id.rlSchedule2);

        llSchedule = (LinearLayout) view.findViewById(R.id.llSchedule);

        llUpcomingMatch = (LinearLayout) view.findViewById(R.id.llUpcomingMatch);
        TextView tvLatest = (TextView) llUpcomingMatch.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvLatest.setText("UPCOMING MATCH");

        ivT1 = (ImageView) view.findViewById(R.id.ivT1);
        ivT2 = (ImageView) view.findViewById(R.id.ivT2);

        ivBanner = (ImageView) view.findViewById(R.id.ivBanner);

        tvVenue = (TextView) view.findViewById(R.id.tvVenue);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvAddToCalendar = (TextView) view.findViewById(R.id.tvAddToCalendar);
        tvVenue.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvTime.setTypeface(CustomFonts.getLightFont(getActivity()));
        tvAddToCalendar.setTypeface(CustomFonts.getRegularFont(getActivity()));

        llOtherMatches = (LinearLayout) view.findViewById(R.id.llOtherMatches);
        TextView tvOther = (TextView) llOtherMatches.findViewById(R.id.tvCrossHeader);
        tvOther.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvOther.setText("OTHER MATCHES");

        tvBook = (TextView) view.findViewById(R.id.tvBook);
        tvBook.setTypeface(CustomFonts.getRegularFont(getActivity()));

        llAddToCalendar = (LinearLayout) view.findViewById(R.id.llAddToCalendar);


        /*TextView tvScore1 = (TextView) view.findViewById(R.id.tvScore1);
        TextView tvScore2 = (TextView) view.findViewById(R.id.tvScore2);
        TextView tvFulltime = (TextView) view.findViewById(R.id.tvFulltime);
        TextView tvReport = (TextView) view.findViewById(R.id.tvReport);
        TextView tvCentre = (TextView) view.findViewById(R.id.tvCentre);

        tvScore1.setTypeface(CustomFonts.getScoreFont(getActivity()));
        tvScore2.setTypeface(CustomFonts.getScoreFont(getActivity()));


        tvFulltime.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvReport.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvCentre.setTypeface(CustomFonts.getRegularFont(getActivity()));

        progressDialog.dismiss();*/

        //String tag = "Jaipur Pink Panthers#Patna Pirates#30 Jan 2016, 20:00";
        //llAddToCalendar.setTag(tag);

        if (InternetOperations.checkIsOnlineViaIP()) {
            getScheduleData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }

    }

    public void getScheduleData() {

        new AsyncTask<Void, Void, String>() {

            boolean done = false;
            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getScheduleAndroid");

                    jsonArray = new JSONArray(response);

                    if (jsonArray.length() != 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            if (i != 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //String id = String.valueOf(i + 1);
                                String team1 = jsonObject.optString("team1");
                                String team2 = jsonObject.optString("team2");
                                String time = jsonObject.optString("starttimedate");
                                String venue = jsonObject.optString("stadium");
                                populate(team1, team2, time,venue);
                            } else {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //String id = String.valueOf(i + 1);
                                String team1 = jsonObject.optString("team1");
                                String team2 = jsonObject.optString("team2");
                                String team1id = jsonObject.optString("team1id");
                                String team2id = jsonObject.optString("team2id");
                                String time = jsonObject.optString("starttimedate");
                                String stadium = jsonObject.optString("stadium");

                                single.put("team1", team1);
                                single.put("team2", team2);
                                single.put("team1id", team1id);
                                single.put("team2id", team2id);
                                single.put("time", time);
                                single.put("stadium", stadium);

                            }
                        }
                    }
                    done = true;

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e){
                    Log.e("JPP", Log.getStackTraceString(e));
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                if (done) {
                    refresh();
                }else{
                    Toast.makeText(getActivity(),"Oops, Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);
    }

    public void refresh() {

        rlSchedule1.setVisibility(View.VISIBLE);
        rlSchedule2.setVisibility(View.VISIBLE);

        String team1Id = single.get("team1id");
        String team2Id = single.get("team2id");

        if (team1Id != null || team2Id != null) {
            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);
            String imageBanner = "drawable://" + R.drawable.schedule_back;

            imageLoader.displayImage(imageUriTeam1, ivT1, options);
            imageLoader.displayImage(imageUriTeam2, ivT2, options);
            imageLoader.displayImage(imageBanner, ivBanner, options);
        }

        if (list.size() > 0) {

            tvVenue.setText(single.get("stadium"));
            tvTime.setText(single.get("time") + "(IST)");
            String tagMain = single.get("team1") + "#" + single.get("team2") + "#" + single.get("time");
            llAddToCalendar.setTag(tagMain);
            llAddToCalendar.setClickable(true);

            for (int i = 0; i < list.size(); i++) {
                LayoutInflater inflator = getActivity().getLayoutInflater();
                View viewScheduleRow = inflator.inflate(R.layout.layout_schedule_single, null, false);

                TextView tvMatch = (TextView) viewScheduleRow.findViewById(R.id.tvMatch); //find the different Views
                TextView tvTime = (TextView) viewScheduleRow.findViewById(R.id.tvTime);
                ImageView ivCalendar = (ImageView) viewScheduleRow.findViewById(R.id.ivCalendar);


                //ImageView ivTickets = (ImageView) viewScheduleRow.findViewById(R.id.ivTickets);
                LinearLayout llTicket = (LinearLayout) viewScheduleRow.findViewById(R.id.llTicket);
                TextView tvBookIndi = (TextView) viewScheduleRow.findViewById(R.id.tvBookIndi);
                tvBookIndi.setTypeface(CustomFonts.getLightFont(getActivity()));
                LinearLayout llCal = (LinearLayout) viewScheduleRow.findViewById(R.id.llCal);

                TextView tvCal = (TextView) viewScheduleRow.findViewById(R.id.tvCal);
                tvCal.setTypeface(CustomFonts.getLightFont(getActivity()));

                TextView tvBo = (TextView) viewScheduleRow.findViewById(R.id.tvBo);
                tvBo.setTypeface(CustomFonts.getLightFont(getActivity()));

                tvMatch.setTypeface(CustomFonts.getRegularFont(getActivity()));
                tvTime.setTypeface(CustomFonts.getLightFont(getActivity()));

                HashMap<String, String> map = list.get(i);

                String team1 = map.get("team1");
                String team2 = map.get("team2");
                String time = map.get("time");
                String venue = map.get("venue");

                if(venue.toUpperCase().equals("JAIPUR")){
                    LinearLayout llBo = (LinearLayout) viewScheduleRow.findViewById(R.id.llBo);
                    llBo.setVisibility(View.VISIBLE);
                    //ivTickets.setVisibility(View.VISIBLE);
                    //llTicket.setVisibility(View.VISIBLE);
                }

                tvMatch.setText(team1 + " VS " + team2);
                tvTime.setText(time + "(IST)");

                String tag = team1 + "#" + team2 + "#" + time;
                ivCalendar.setTag(tag);
                llCal.setTag(tag);

                llSchedule.addView(viewScheduleRow);
            }
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String team1, String team2, String time, String venue) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("team1", team1);
        map.put("team2", team2);
        map.put("time", time);
        map.put("venue", venue);
        list.add(map);
    }

    public int getTeamDrawable(String id) {

        int teamId = Integer.parseInt(id);
        TypedArray teamLogos = getActivity().getResources().obtainTypedArray(R.array.teamLogo);

        return teamLogos.getResourceId(teamId - 1, -1);
    }
}
