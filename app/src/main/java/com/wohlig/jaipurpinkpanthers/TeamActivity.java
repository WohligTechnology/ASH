package com.wohlig.jaipurpinkpanthers;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

public class TeamActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.layout_team);
    }
}
