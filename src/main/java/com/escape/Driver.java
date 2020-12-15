package com.escape;

import com.escape.controller.Initializer;
import com.escape.controller.Traversal;
import com.escape.models.City;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Driver {
    static HashMap<String, HashMap<String, City>> continents = new HashMap<>();
    static HashMap<String, HashMap<String, Double>> intraCityDistance = new HashMap<>();
    static HashMap<String, String> city_continent_map = new HashMap<>();

    public static void main(String[] args) {
        Initializer initializer = new Initializer("src/main/resources/cities.json");
        initializer.prepareData();

        continents = initializer.getContinents();
        city_continent_map = initializer.getCityContinentMap();
        intraCityDistance = initializer.getIntraCityDistance();

        System.out.println("Continents:  Number of Cities");
        for(String continent: continents.keySet()){
            System.out.print(continent+":  "+continents.get(continent).size()+",  ");
        }

        System.out.println("\n\nMinimum of `ABSOLUTE NUMBER of Neighbours to consider` and `PERCENTAGE of Neighbours to consider` will be taken.\n");
        Scanner sc= new Scanner(System.in);

        System.out.print("Please enter ABSOLUTE NEIGHBOURS you want to traverse (Put any number above 1200 for traversing all neighbours): ");
        int minimumAbsoluteNeighbours= Integer.parseInt(sc.nextLine().split(" ")[0]);

        System.out.print("Please enter PERCENTAGE(0.0 - 1.0) NEIGHBOURS you want to traverse (Put 1 for traversing all neighbours): ");
        double minimumPercentageNeighbours = Double.parseDouble(sc.nextLine().split(" ")[0]);

        Traversal traversal = new Traversal(minimumAbsoluteNeighbours, minimumPercentageNeighbours,
                continents, city_continent_map, intraCityDistance);

        System.out.println("Please enter number of Test cases you want to run: ");
        int t =  Integer.parseInt(sc.nextLine().split(" ")[0]);

        StopWatch stopwatch;
        stopwatch = new StopWatch();

        while (t-- > 0){
            System.out.print("Enter IATA: ");
            String city_code = sc.nextLine().toUpperCase();
            System.out.println("Processing...");

            if(!city_continent_map.containsKey(city_code)){
                System.out.println(city_code+" is not a valid IATA. Retry!");
                t++;
                continue;
            }

            stopwatch.start();
            traversal.startRoundTrip(city_code);
            stopwatch.stop();
            System.out.println("Total time taken(seconds): "+(double)stopwatch.getTime()/1000);

            System.out.println("\n\n");
            stopwatch.reset();
        }
    }

}
