package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.WebActivity;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.FormValidation;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FanFragment extends Fragment {
    private View view;
    private TextView tvSign, tvFan1, tvFan2, tvSubmit;
    private EditText etFirst, etLast, etEmail, etMob;
    private ImageView ivFb, ivTw, ivInsta;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fan, container, false);

        initilizeViews();
        setListeners();
        return view;
    }

    private void initilizeViews() {

        activity = getActivity();
        tvSign = (TextView) view.findViewById(R.id.tvSign);
        tvFan1 = (TextView) view.findViewById(R.id.tvFan1);
        tvFan2 = (TextView) view.findViewById(R.id.tvFan2);
        etFirst = (EditText) view.findViewById(R.id.etFirst);
        etLast = (EditText) view.findViewById(R.id.etLast);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etMob = (EditText) view.findViewById(R.id.etMob);
        tvSubmit = (TextView) view.findViewById(R.id.tvSubmit);

        tvSign.setTypeface(CustomFonts.getRegularFont(activity));
        tvSubmit.setTypeface(CustomFonts.getRegularFont(activity));
        tvFan2.setTypeface(CustomFonts.getRegularFont(activity));

        tvFan1.setTypeface(CustomFonts.getLightFont(activity));
        etFirst.setTypeface(CustomFonts.getLightFont(activity));
        etLast.setTypeface(CustomFonts.getLightFont(activity));
        etMob.setTypeface(CustomFonts.getLightFont(activity));
        etEmail.setTypeface(CustomFonts.getLightFont(activity));

        ivFb = (ImageView) view.findViewById(R.id.ivFb);
        ivTw = (ImageView) view.findViewById(R.id.ivTw);
        ivInsta = (ImageView) view.findViewById(R.id.ivInsta);
    }

    private void setListeners() {

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = etFirst.getText().toString().trim();
                String lName = etLast.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etMob.getText().toString().trim();

                boolean valid = true;

                if (fName.isEmpty()) {
                    etFirst.setError("Enter First Name");
                    valid = false;
                } else {
                    etFirst.setError(null);
                }


                if (lName.isEmpty()) {
                    etLast.setError("Enter Last Name");
                    valid = false;
                } else {
                    etLast.setError(null);
                }


                if (phone.isEmpty()) {
                    etMob.setError("Enter Mobile Number");
                    valid = false;
                } else if (!FormValidation.isValidMobile(phone)) {
                    etMob.setError("Enter Valid Mobile Number");
                    valid = false;
                } else {
                    etMob.setError(null);
                }


                if (email.isEmpty()) {
                    etEmail.setError("Enter Email");
                    valid = false;
                } else if (!FormValidation.isValidEmail(email)) {
                    etEmail.setError("Enter Valid Email");
                    valid = false;
                } else {
                    etEmail.setError(null);
                }


                if (valid) {
                    submit(fName, lName, phone, email);
                    Toast.makeText(activity, "Thank you!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("webLink", "https://www.facebook.com/JaipurPinkPanthers/");
                startActivity(intent);
            }
        });

        ivTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("webLink", "https://twitter.com/JaipurPanthers");
                startActivity(intent);
            }
        });

        ivInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("webLink", "https://www.instagram.com/jaipur_pinkpanthers/");
                startActivity(intent);
            }
        });

    }

    public void submit(final String fName, final String lName, final String mobile, final String email){

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();



        new AsyncTask<Void, Void, String>() {
            boolean submit = false;

            @Override
            protected String doInBackground(Void... params) {

                try {
                    String submitJson = getSubmitJson(fName, lName, mobile, email).toString();
                    Log.e("jay sharejson", submitJson);
                    String response = InternetOperations.post(InternetOperations.SERVER_URL + "contactus", submitJson);
                    Log.e("jay response", response);

                    JSONObject jsonObject = new JSONObject(response);

                    String value = jsonObject.optString("value");
                    if (value.equals("true")) {
                        progressDialog.dismiss();
                        submit = true;
                    } else {
                        progressDialog.dismiss();
                        submit = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (submit) {
                    Toast.makeText(activity, "Thank you!!!", Toast.LENGTH_LONG).show();
                    etFirst.setText("");
                    etLast.setText("");
                    etMob.setText("");
                    etEmail.setText("");

                }else
                    Toast.makeText(activity, "Oops, Something went wrong!", Toast.LENGTH_LONG).show();

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }.execute(null, null, null);
    }

    public JSONObject getSubmitJson(String fName, String lName, String mobile, String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", fName);
            jsonObject.put("lastname", lName);
            jsonObject.put("mobile", mobile);
            jsonObject.put("email", email);
        } catch (JSONException je) {
        }
        return jsonObject;
    }
}
