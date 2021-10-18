package com.train.machine.component;

import com.train.machine.entities.StationsMatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StationsComponent {
    @Autowired
    @Qualifier("listStations")
    private List<String> mapping;

    public StationsMatchDto getStations(String station) {
        var stations = mapping.stream()
                .filter(f ->
                        f.regionMatches(true, 0, station, 0, station.length()))
                .collect(Collectors.toMap(
                        r -> r,
                        r ->  station.length() >= r.length() ? '\0' : r.charAt(station.length()))
                );

        return StationsMatchDto.builder()
                .stations(stations.keySet().stream().toList())
                .nextCharMatches(stations.values().stream().filter(f -> f.charValue() != '\0').collect(Collectors.toSet())
                        .stream().map(r -> r.charValue()).collect(Collectors.toList()))
                .build();
    }
}
