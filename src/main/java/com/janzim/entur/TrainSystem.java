package com.janzim.entur;

import com.janzim.entur.data.Route;
import com.janzim.entur.data.Stop;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class TrainSystem {
    private final List<Route> routes = new ArrayList<>();

    /**
     * Oppgave 1
     */
    public Route addRoute(String routeName, List<Stop> stops) {
        stops.sort(Comparator.comparing(Stop::getTravelTime));

        Route route = new Route(routeName, stops);

        routes.add(route);
        return route;
    }

    /**
     * Oppgave 2
     */
    public Duration calculateTravelTime(String routeName, String stationName1, String stationName2) {
        Route route = getRoute(routeName)
                .orElseThrow(() -> new IllegalArgumentException("A route with name "+routeName+" was not found"));

        Stop stop1 = route.getStop(stationName1)
                .orElseThrow(() -> new IllegalArgumentException("At least one of the stations are not part of this route"));

        Stop stop2 = route.getStop(stationName2)
                .orElseThrow(() -> new IllegalArgumentException("At least one of the stations are not part of this route"));

        return stop1.getTravelTime().minus(stop2.getTravelTime()).abs();
    }

    /**
     * Oppgave 3
     * (Antar at det er snakk om tidspunkt fra avgangsstart, og ikke et klokkeslett)
     */
    public List<Route> getRoutesThatStopAt(Duration maxTime, String stationName) {
        return routes.stream()
                .filter(r -> r.getStop(stationName).isPresent() &&
                        r.getStop(stationName).get().getTravelTime().compareTo(maxTime) <= 0)
                .toList();
    }

    /**
     * Oppgave 4
     */
    public int passengersTravelling(String routeName, LocalTime departure) {
        Route route = getRoute(routeName)
                .orElseThrow(() -> new IllegalArgumentException("A route with name "+routeName+" was not found"));

       /*
        Ikke nok tid, men pseudocode:
        - Legge til en metode getPopulation(String) for å få antall innbyggere i en kommune

        total = 0
        for (int i = 0; i < stops.size()-1; i++)
            Stop stop = stops[i]
            stationTime = departure + stop.getTravelTime()
            travellersMultiplier = 1.0 / frequency-of-municipality-in-Route
            stopPopulation = getPopulation(stop.getMunicipality())
            timeMultiplier;

            if (stationTime >= 06 && stationTime < 09)
                timeMultiplier = 0.02
            else if (stationTime >= 09 && stationTime < 15)
                timeMultiplier = 0.005
            else
                timeMultiplier = 0.01

            total += timeMultiplier * stopPopulation * travellersMultiplier

        return total;
        */

        return 0;
    }

    private Optional<Route> getRoute(String routeName) {
        return routes.stream()
                .filter(r -> r.getRouteName().equals(routeName))
                .findAny();
    }
}
