package com.train.machine.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StationsMatchDto {
    List<String> stations;
    List<Character> nextCharMatches;
}
