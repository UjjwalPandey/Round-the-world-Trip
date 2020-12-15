package com.escape.controller;

import com.escape.models.City;
import com.escape.models.NeighbouringCity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Traversal {
    static City startCity;
    private final HashMap<String, HashMap<String, City>> continents;
    private final HashMap<String, String> city_continent_map;
    private final HashMap<String, HashMap<String, Double>> intraCityDistance;
    static ArrayList<String> cities_visited;
    static double minimum_distance;
    private final int minimumAbsoluteNeighbours;
    private final double minimumPercentageNeighbours;

    public Traversal(int minimumAbsoluteNeighbours,
                     double minimumPercentageNeighbours,
                     HashMap<String, HashMap<String, City>> continents,
                     HashMap<String, String> city_continent_map,
                     HashMap<String, HashMap<String, Double>> intraCityDistance) {
        this.minimumPercentageNeighbours = minimumPercentageNeighbours;
        this.minimumAbsoluteNeighbours = minimumAbsoluteNeighbours;
        this.continents = continents;
        this.city_continent_map = city_continent_map;
        this.intraCityDistance = intraCityDistance;
    }


    public void startRoundTrip(String city_code) {
        minimum_distance = Integer.MAX_VALUE;
        cities_visited = new ArrayList<>();
        startCity = continents.get(city_continent_map.get(city_code)).get(city_code);
        HashSet<String> remainingContinents = new HashSet<>(continents.keySet());
        remainingContinents.remove(startCity.getContinent());
        ArrayList<String> visitedOrder = new ArrayList<>();
        visitedOrder.add(startCity.getName()+"/"+startCity.getContinent());
        nextCity(startCity, visitedOrder, remainingContinents, 0.0);
        cities_visited.add(startCity.getName()+"/"+startCity.getContinent());
        System.out.println(cities_visited);
        System.out.println("MINIMUM DISTANCE (in KM):  "+minimum_distance);
    }

    private boolean nextCity(City currentCity, ArrayList<String> visitedOrder, HashSet<String> remainingContinents, double totalDistance) {
        boolean returnFlag = false;
        if(remainingContinents.size() == 0){
            totalDistance += intraCityDistance.get(currentCity.getCityCode()).get(startCity.getCityCode());
            if(minimum_distance > totalDistance) {
                minimum_distance = totalDistance;
                cities_visited = new ArrayList<>(visitedOrder);
                return true;
            }
            return false;
        }else {
            String[] loopOnContinent;
            loopOnContinent = remainingContinents.toArray(new String[0]);
            for (String continent : loopOnContinent) {
                HashSet<String> afterContentRemoval = new HashSet<>(remainingContinents);
                afterContentRemoval.remove(continent);
                boolean shouldBreakLoop = false;
                for (int j = 0;
                     j < Math.min(minimumAbsoluteNeighbours,
                             minimumPercentageNeighbours*currentCity.getDistanceWithOtherCities().get(continent).size());
                     j++) {
                    NeighbouringCity neighbouringCity = currentCity.getDistanceWithOtherCities().get(continent).get(j);
                    double distance = neighbouringCity.getDistance();
                    if (totalDistance + distance > minimum_distance){
                        return false;
                    }
                    City nextCity = continents.get(continent).get(neighbouringCity.getCityCode());
                    visitedOrder.add(nextCity.getName()+"/"+nextCity.getContinent());
                    shouldBreakLoop = !nextCity(nextCity, visitedOrder, afterContentRemoval, totalDistance + distance);
                    visitedOrder.remove(nextCity.getName()+"/"+nextCity.getContinent());
                    if(shouldBreakLoop){
                        break;
                    }
                }

                returnFlag |= shouldBreakLoop;
            }
        }
        return  returnFlag;
    }

}
