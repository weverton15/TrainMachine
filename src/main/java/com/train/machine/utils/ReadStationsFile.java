package com.train.machine.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ReadStationsFile {

    @Bean(name = "listStations")
    public List<String> readFromInputStream() {
        Class clazz = ReadStationsFile.class;
        InputStream inputStream = clazz.getResourceAsStream("/StationsPT.txt");
        List<String> listStations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                listStations.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listStations;
    }

}
