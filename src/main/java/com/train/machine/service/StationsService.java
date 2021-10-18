package com.train.machine.service;

import com.train.machine.component.StationsComponent;
import com.train.machine.entities.StationsMatchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
@RequiredArgsConstructor
@Slf4j
public class StationsService {
    private final StationsComponent statusComponent;

    public StationsMatchDto getStations(String station) {
        return statusComponent.getStations(station);
    }
}
