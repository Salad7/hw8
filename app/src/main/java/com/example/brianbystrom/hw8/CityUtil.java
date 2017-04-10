package com.example.brianbystrom.hw8;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
Assignment #: HW 08
File Name: CityUtil.java
Group Members: Brian Bystrom, Mohamed Salad
*/


public class CityUtil {

    static public class DataJSONParser {
        static ArrayList<City> parseData(String in) {
            ArrayList<City> cityList = new ArrayList();

            try {
                JSONArray root = new JSONArray(in);

                for (int i = 0; i<root.length(); i++) {

                    City city = new City();


                    JSONObject dataJSONObject = root.getJSONObject(i);
                    city.setKey(dataJSONObject.getString("Key"));
                    city.setName(dataJSONObject.getString("LocalizedName"));

                    JSONObject countryJSONObject = dataJSONObject.getJSONObject("Country");

                    city.setCountry(countryJSONObject.getString("ID"));




                    cityList.add(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return cityList;
        }
    }
}
