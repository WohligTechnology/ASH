package com.wohlig.jaipurpinkpanthers.util;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Jay on 27-01-2016.
 */
public class InternetOperations {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    public static String SERVER_URL = "http://192.168.0.124/jppbackend/index.php/json/";

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Log.e("JPP response", response.toString());
        return response.body().string();
    }

    public static String postBlank(String url) throws IOException {
        //RequestBody body = RequestBody.create(JSON, getNotificationsJson().toString());
        Log.e("JPP link", url);
        Request request = new Request.Builder()
                .url(url)
                //.post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static JSONObject getNotificationsJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user", "jay");
        } catch (JSONException je) {

        }
        return jsonObject;
    }

}
