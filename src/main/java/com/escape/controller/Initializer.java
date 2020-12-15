package com.escape.controller;

import com.escape.models.City;
import com.escape.models.NeighbouringCity;
import com.escape.utils.Commons;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Initializer {
    private static final HashMap<String, HashMap<String, City>> continents =  new HashMap<>();
    private static final HashMap<String, String> cityContinentMap =  new HashMap<>();
    private static final HashMap<String, HashMap<String, Double>> intraCityDistance = new HashMap<>();
    String filePath;


    public Initializer(String filePath) {
        this.filePath = filePath;
    }

    public void prepareData() {
        System.out.println("Preparing Data. Please Wait!!!");
        JSONObject jsonObject = Commons.jsonParser(this.filePath);
        prepareContinentCityMap(jsonObject);
        populateNeighboursForEachCity();
        System.out.println("Data Prepared!");
    }

    private static void prepareContinentCityMap(JSONObject jsonObject) {
        for (Object object : jsonObject.keySet()) {
            JSONObject city_json = (JSONObject) jsonObject.get(object);
            String continent_name = city_json.get("contId").toString();
            City city = new City((JSONObject) city_json.get("location"), city_json.get("iata").toString(),
                    continent_name, city_json.get("name").toString());
            HashMap<String, City> cities_in_continent= continents.getOrDefault(continent_name, new HashMap<>());
            cities_in_continent.put(city_json.get("iata").toString(), city);
            continents.put(continent_name, cities_in_continent);
            cityContinentMap.put(city_json.get("iata").toString(), continent_name);
        }
    }

    private static void populateNeighboursForEachCity() {
        ArrayList<String> continent_list = new ArrayList<>(continents.keySet());
        for (String continent_name : continent_list){
            for(City city: continents.get(continent_name).values()){
                createNeighbours(city);
            }
        }
    }

    private static void createNeighbours(City city) {
        HashMap<String, ArrayList<NeighbouringCity>> distance_with_other_cities = new HashMap<>();
        HashMap<String, Double> intraCityMap = intraCityDistance.getOrDefault(city.getCityCode(), new HashMap<>());
        for (String other_continent : continents.keySet()){
            if(other_continent.equals(city.getContinent())) continue;
            ArrayList<NeighbouringCity> destinationCitiesWithDistance = new ArrayList<>();
            for(City other_city: continents.get(other_continent).values()){
                double distance = Commons.getDistanceFromLatLonInKm(city, other_city);
                intraCityMap.put(other_city.getCityCode(), distance);
                destinationCitiesWithDistance.add(new NeighbouringCity(other_city.getName(), other_continent, other_city.getCityCode(), distance));
            }
            destinationCitiesWithDistance.sort((destination1, destination2) -> (int) (destination1.getDistance() - destination2.getDistance()));
            distance_with_other_cities.put(other_continent, destinationCitiesWithDistance);
        }
        intraCityDistance.put(city.getCityCode(), intraCityMap);
        city.setDistanceWithOtherCities(distance_with_other_cities);
    }

    public HashMap<String, HashMap<String, City>> getContinents() {
        return continents;
    }

    public HashMap<String, String> getCityContinentMap() {
        return cityContinentMap;
    }

    public HashMap<String, HashMap<String, Double>> getIntraCityDistance() {
        return intraCityDistance;
    }
}
