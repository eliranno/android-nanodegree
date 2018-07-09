package com.example.elirannoach.bakingapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String BAKING_RECIPE_URL_STRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";




    public static String getUrlData(URL url){
        try {
            InputStream in = url.openConnection().getInputStream();
            Scanner scanner =  new Scanner(in);
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static JSONObject getJsonUrlData(URL url){
        String data = getUrlData(url);
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
