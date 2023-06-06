package com.janzim.entur.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class Route {
    private final String routeName;
    private List<Stop> stops;

    public Optional<Stop> getStop(String stationName) {
        return stops.stream()
                .filter(s -> s.getStationName().equals(stationName))
                .findAny();
    }
}
