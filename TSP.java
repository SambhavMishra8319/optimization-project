package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TSP {
    private ArrayList<City> cities = new ArrayList<>();

    public void loadCities(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double latitude = Double.parseDouble(parts[1]);
                double longitude = Double.parseDouble(parts[2]);
                cities.add(new City(name, latitude, longitude));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateDistance(City city1, City city2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(city2.getLatitude() - city1.getLatitude());
        double lonDistance = Math.toRadians(city2.getLongitude() - city1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                   Math.cos(Math.toRadians(city1.getLatitude())) * Math.cos(Math.toRadians(city2.getLatitude())) *
                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public ArrayList<City> findShortestPath() {
        ArrayList<City> shortestPath = new ArrayList<>(cities);
        Collections.shuffle(shortestPath); // Random initial path

        double bestDistance = calculateTotalDistance(shortestPath);
        boolean improved = true;

        while (improved) {
            improved = false;
            for (int i = 1; i < cities.size() - 1; i++) {
                for (int j = i + 1; j < cities.size(); j++) {
                    ArrayList<City> newPath = new ArrayList<>(shortestPath);
                    Collections.swap(newPath, i, j);
                    double newDistance = calculateTotalDistance(newPath);

                    if (newDistance < bestDistance) {
                        bestDistance = newDistance;
                        shortestPath = newPath;
                        improved = true;
                    }
                }
            }
        }
        return shortestPath;
    }

    public double calculateTotalDistance(ArrayList<City> path) {
        double totalDistance = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += calculateDistance(path.get(i), path.get(i + 1));
        }
        totalDistance += calculateDistance(path.get(path.size() - 1), path.get(0)); // Return to start
        return totalDistance;
    }

    public static void main(String[] args) {
        TSP tsp = new TSP();
        
        System.out.println("Attempting to load cities from file...");
        tsp.loadCities("D:/Dataset/UK_Cities.csv");
        System.out.println("File loaded successfully.");
        System.out.println("Number of cities loaded: " + tsp.cities.size());

        if (tsp.cities.size() == 0) {
            System.out.println("No cities loaded. Please check the file path and format.");
            return;
        }

        ArrayList<City> shortestPath = tsp.findShortestPath();
        System.out.println("Shortest path:");
        for (City city : shortestPath) {
            System.out.print(city.getName() + " -> ");
        }
        System.out.println(shortestPath.get(0).getName()); // Back to the starting city

        double totalDistance = tsp.calculateTotalDistance(shortestPath);
        System.out.printf("Total distance: %.2f km%n", totalDistance);
    }

}
