package com.wohlig.jaipurpinkpanthers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.wohlig.jaipurpinkpanthers.R;
import com.wohlig.jaipurpinkpanthers.adapters.PanthersAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PanthersFragment extends Fragment {

    public GridView gvPlayers;
    public View view;
    private ArrayList<HashMap<String,String>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_panthers, container, false);


        //int id = ((MainActivity)this.getActivity()).getPlayerId();


        /*String player = getResources().getString(R.string.player1);
        List<String> playerDetail = Arrays.asList(player.split("#"));

        String playerName = playerDetail.get(0);                //PlayerName
        String playerType = playerDetail.get(1);                //PlayerType
        String playerNationality = playerDetail.get(2);         //PlayerNationality
        String playerDob = playerDetail.get(3);                 //PlayerDob
        String playerJerseyNum = playerDetail.get(4);           //PlayerJerseyNum
        String playerDesc = playerDetail.get(5);                //PlayerDesc
*/
        /*LinearLayout layoutPlayer = (LinearLayout) view.findViewById(R.id.ganga);
        TextView layoutPlayerName = (TextView) layoutPlayer
                .findViewById(R.id.player_name);

        TextView layoutListPlayerType = (TextView) layoutPlayer
                .findViewById(R.id.player_type);
        //"RAJU LAL CHOUDHARY"
        //layoutPlayerName.setText(String.valueOf(id));
        layoutPlayerName.setTypeface(CustomFonts.getProfileFont(getActivity()));
        layoutListPlayerType.setTypeface(CustomFonts.getProfileFont(getActivity()));
        layoutPlayer.setTag("3");

        ((MainActivity) this.getActivity()).setPlayerId(333);*/

        initilizeView();

        Log.v("JPP","Inside pF oCV");

        return view;
    }

    public void initilizeView(){

        gvPlayers = (GridView) view.findViewById(R.id.gvPlayers);

        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();

        String[] players = getResources().getStringArray(R.array.player);

        for(int i = 0; i < players.length; i ++)
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("PlayerInfo", players[i]); //this will send id, player name and image
            list.add(map);
        }

        PanthersAdapter panthersAdapter = new PanthersAdapter(getActivity(),list);

        gvPlayers.setAdapter(panthersAdapter);
    }

}
