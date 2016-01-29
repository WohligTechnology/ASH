package com.jaipurpinkpanthers.android.util;

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
    //public static String SERVER_URL = "http://192.168.0.124/jppbackend/index.php/json/";
    //public static String SERVER_UPLOADS_URL = "http://192.168.0.124/jppbackend/uploads/";
    public static String SERVER_URL = "http://www.wohlig.co.in/jppbackend/index.php/json/";
    public static String SERVER_UPLOADS_URL = "http://www.wohlig.co.in/jppbackend/uploads/";

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postBlank(String url) throws IOException {
        //RequestBody body = RequestBody.create(JSON, getNotificationsJson().toString());
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


    public static boolean checkIsOnlineViaIP() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return false;
    }
}
