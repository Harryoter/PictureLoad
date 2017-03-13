package com.example.hasee.pictureload.Utility;

import android.util.Log;

import com.example.hasee.pictureload.json.Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by hasee on 2017/3/10.
 */

public class ParseJsonWithHttpURLConnection {

    public static String getJsonData(String uri){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String inputLine = null;
            while ((inputLine = reader.readLine())!= null){
                stringBuilder.append(inputLine);
            }

            in.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static ArrayList<Results> parseMeiziJSON(String jsonData){
        List<Results> meizis = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonData);
            JSONArray jsonArray = object.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("_id");
                String createdAt = jsonObject.getString("createdAt");
                String desc = jsonObject.getString("desc");
                String publishedAt = jsonObject.getString("publishedAt");
                String source = jsonObject.getString("source");
                String type = jsonObject.getString("type");
                String url= jsonObject.getString("url");
                String used = jsonObject.getString("used");
                String who = jsonObject.getString("who");
                Log.d("MainActivity","url"+url);
                Results meizi = new Results();
                meizi.setCreatedAt(createdAt);
                meizi.setDesc(desc);
                meizi.setId(id);
                meizi.setUrl(url);
                meizi.setPublishedAt(publishedAt);
                meizi.setSource(source);
                meizi.setUsed(used);
                meizi.setWho(who);
                meizi.setType(type);
                Log.d(TAG, "parseMeiziJSON: "+url);
                meizis.add(meizi);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (ArrayList<Results>) meizis;
    }
}
