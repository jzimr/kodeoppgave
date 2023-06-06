package com.janzim.entur;

import com.janzim.entur.data.Route;
import com.janzim.entur.data.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrainSystemTest {
    private TrainSystem trainSystem;
    private List<Stop> route_R10;

    @BeforeEach
    public void setup() {
        trainSystem = new TrainSystem();

        route_R10 = new ArrayList<>(List.of(
                new Stop(Duration.ZERO, "Lillehammer", "Lillehammer kommune"),
                new Stop(Duration.ofMinutes(30), "Moelv", "Ringsaker kommune"),
                new Stop(Duration.ofMinutes(60), "Brumunddal", "Ringsaker kommune"),
                new Stop(Duration.ofMinutes(80), "Stange", "Stange kommune"),
                new Stop(Duration.ofMinutes(100), "Tangen", "Stange kommune"),
                new Stop(Duration.ofMinutes(120), "Oslo Lufthavn", "Ullensaker kommune"),
                new Stop(Duration.ofMinutes(150), "Oslo S", "Oslo")
        ));
    }

    @Test
    public void testAddRouteSuccess() {
        Route route = trainSystem.addRoute("R10", route_R10);

        assertEquals("R10", route.getRouteName());
        assertEquals(route_R10, route.getStops());
    }

    @Test
    public void testAddRouteIsSorted() {
        Collections.shuffle(route_R10);

        Route route = trainSystem.addRoute("R10", route_R10);
        assertEquals(route_R10, route.getStops());
    }

    @Test
    public void testCalculateTravelTimeSuccess() {
        trainSystem.addRoute("R10", route_R10);

        Duration time = trainSystem.calculateTravelTime("R10", "Lillehammer", "Tangen");
        assertEquals(100, time.toMinutes());

        Duration timeReversed = trainSystem.calculateTravelTime("R10", "Tangen", "Lillehammer");
        assertEquals(100, timeReversed.toMinutes());
    }

    @Test
    public void testCalculateTravelTimeInvalidRoute() {
        trainSystem.addRoute("R10", route_R10);

        assertThrows(IllegalArgumentException.class, () -> trainSystem.calculateTravelTime("R11", "Oslo S", "Stange"));
    }

    @Test
    public void testCalculateTravelTimeInvalidStation() {
        trainSystem.addRoute("R10", route_R10);

        assertThrows(IllegalArgumentException.class, () -> trainSystem.calculateTravelTime("R10", "Horten", "Skien"));
    }

    @Test
    public void testRouteStopsAtSuccess() {
        trainSystem.addRoute("R10", route_R10);

        List<Route> route = trainSystem.getRoutesThatStopAt(Duration.ofMinutes(30), "Moelv");
        assertEquals(1, route.size());
        assertEquals("R10", route.get(0).getRouteName());

        route = trainSystem.getRoutesThatStopAt(Duration.ofMinutes(120), "Oslo S");
        assertEquals(0, route.size());
    }

    @Test
    public void testRouteStopsAtInvalidStation() {
        trainSystem.addRoute("R10", route_R10);

        List<Route> route = trainSystem.getRoutesThatStopAt(Duration.ofMinutes(30), "Tønsberg");
        assertEquals(0, route.size());
    }
}
