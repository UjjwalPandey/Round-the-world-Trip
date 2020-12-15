package com.escape.models;

public class NeighbouringCity {
    String name;
    String cityCode;
    double distance;
    String continent;

    public NeighbouringCity(String name, String continent, String city_code, double distance) {
        this.name = name;
        this.cityCode = city_code;
        this.distance = distance;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getContinent() {
        return continent;
    }

    @Override
    public String toString() {
        return "DestinationCity{" +
                "name='" + name + '\'' +
                ", city_code='" + cityCode + '\'' +
                ", continent='" + continent + '\'' +
                ", distance=" + distance +
                '}';
    }
}
