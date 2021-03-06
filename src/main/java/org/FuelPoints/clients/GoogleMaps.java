package org.FuelPoints.clients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.FuelPoints.entities.Trip;
import org.FuelPoints.vessels.googlemaps.DirectionsResult;
import org.FuelPoints.vessels.googlemaps.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GoogleMaps {

    private static String YOUR_API_KEY = "";
    private static String BASE_URL = "https://maps.googleapis.com/maps/api/directions/json?origin=";

    public static DirectionsResult retrieveDirections(String origin, String destination) {
        RestTemplate restTemplate = new RestTemplate();
        DirectionsResult result = restTemplate.getForObject(BASE_URL + origin + "&destination=" + destination + "&alternatives=true&key=" + YOUR_API_KEY, DirectionsResult.class);
        return result;
    }

    public static String retrieveJsonDirections(String origin, String destination){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(BASE_URL + origin + "&destination=" + destination + "&alternatives=true&key=" + YOUR_API_KEY, String.class);
        return result;
    }

    public static ArrayList<Trip> convertDirectionsResultToTrips(DirectionsResult result) {
        ArrayList<Trip> listOfTrips = new ArrayList<>();
        Trip trip = new Trip();
        for (Route route : result.getRoutes()) {
            trip.setOrigin(route.getLegs().get(0).getStart_address().toString());       //todo: do some routes have multiple legs? reconcile
            trip.setDestination(route.getLegs().get(0).getEnd_address().toString());
            trip.setTotalDistance(route.getLegs().get(0).getDistance().getValue());
            trip.setTotalDuration(route.getLegs().get(0).getDuration().getValue());
            listOfTrips.add(trip);
        }
        return listOfTrips;
    }


}
