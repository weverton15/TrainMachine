package com.train.machine.controller;

import com.train.machine.entities.StationsMatchDto;
import com.train.machine.service.StationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class StationsController {
    private final StationsService stationsService;

    @GetMapping("/stations")
    public ResponseEntity<StationsMatchDto> getStations(@RequestParam("station") String station) {
        return new ResponseEntity<>(stationsService.getStations(station), HttpStatus.OK);
    }
}
