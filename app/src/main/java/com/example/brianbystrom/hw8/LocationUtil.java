package com.example.brianbystrom.hw8;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
Assignment #: HW 08
File Name: LocationUtil.java
Group Members: Brian Bystrom, Mohamed Salad
*/



public class LocationUtil {

    static public class DataJSONParser {
        static ArrayList<Location> parseData(String in) {
            ArrayList<Location> locationsList = new ArrayList();

            try {
                JSONObject root = new JSONObject(in);

                for (int i = 0; i<root.length(); i++) {

                    Location location = new Location("");
                    location.setKey(root.get("Key").toString());




                    locationsList.add(location);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return locationsList;
        }
    }
}
