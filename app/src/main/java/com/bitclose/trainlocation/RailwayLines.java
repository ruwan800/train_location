package com.bitclose.trainlocation;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RailwayLines {

    private static RailwayLines instance;

    public JSONObject getJo() {
        return jo;
    }

    private JSONObject jo;

    private RailwayLines(Context context) {
        jo = loadJSONFromAsset(context);
    }

    public static RailwayLines getInstance(Context context) {
        if(instance == null) {
            instance = new RailwayLines(context);
        }
        return instance;
    }

    public static JSONObject loadJSONFromAsset(Context context) {
        String json = null;
        try {
            AssetManager manager = context.getAssets();
            InputStream is = manager.open("railway_sorted.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            return obj;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, List<List<Float>>> getLines() throws JSONException {
        Map<String, List<List<Float>>> lines = new HashMap<>();
        Iterator<?> keys = jo.keys();
        while( keys.hasNext() ) {
            List<List<Float>> points = new ArrayList<>();
            String key = (String)keys.next();
            JSONArray jPoints = (JSONArray)jo.get(key);
            for(int i = 0, len = jPoints.length(); i < len; i++) {
                JSONArray p = jPoints.getJSONArray(i);
                float x = (float)((((float)p.getDouble(0)) - 79.6) * 500);
                float y = (float)(2000 - ((((float)p.getDouble(1)) - 5.9) * 500));
                points.add(new ArrayList<>(Arrays.asList(x, y)));
            }
            lines.put(key, points);
        }
        return lines;
    }

}
