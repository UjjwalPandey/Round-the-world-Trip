package com.escape.models;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class City {
    double lat, lon;
    String cityCode;
    String name;
    String continent;
    HashMap<String, ArrayList<NeighbouringCity>> distanceWithOtherCities = new HashMap<>();

    public City(JSONObject location, String city_code, String continent, String name) {
        this.lat = (double) location.get("lat");
        this.lon = (double) location.get("lon");
        this.cityCode = city_code;
        this.continent = continent;
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", city_code='" + cityCode + '\'' +
                ", continent='" + continent + '\'' +
                ", name='" + name + '\'' +
                ", distances ='" + distanceWithOtherCities + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getContinent() {
        return continent;
    }

    public HashMap<String, ArrayList<NeighbouringCity>> getDistanceWithOtherCities() {
        return distanceWithOtherCities;
    }

    public void setDistanceWithOtherCities(HashMap<String, ArrayList<NeighbouringCity>> distanceWithOtherCities) {
        this.distanceWithOtherCities = distanceWithOtherCities;
    }
}
