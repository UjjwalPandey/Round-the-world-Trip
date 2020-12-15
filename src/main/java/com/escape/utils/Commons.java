package com.escape.utils;

import com.escape.models.City;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Commons {
    public static JSONObject jsonParser(String filePath){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
             jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public static double getDistanceFromLatLonInKm(City city1, City city2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(city2.getLat()-city1.getLat());  // deg2rad below
        double dLon = deg2rad(city2.getLon()-city1.getLon());
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(city1.getLat())) * Math.cos(deg2rad(city2.getLat())) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}
