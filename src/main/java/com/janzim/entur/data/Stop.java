package com.janzim.entur.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
public class Stop {
    private Duration travelTime;    // from start
    private String stationName;
    private String municipality;
}
