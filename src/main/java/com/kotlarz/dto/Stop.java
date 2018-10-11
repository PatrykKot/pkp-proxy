package com.kotlarz.dto;

import com.kotlarz.peka.adapter.dto.PekaStopPoint;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stop {
    private String name;

    private String symbol;

    public static Stop from(PekaStopPoint pekaStopPoint) {
        return Stop.builder()
                .name(pekaStopPoint.getName())
                .symbol(pekaStopPoint.getSymbol())
                .build();
    }
}
