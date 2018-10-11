package com.kotlarz.dto;

import com.kotlarz.peka.adapter.dto.PekaDirection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Direction {
    private Boolean returnVariant;

    private String direction;

    private String lineName;

    public static Direction from(PekaDirection direction) {
        return Direction.builder()
                .returnVariant(direction.getReturnVariant())
                .direction(direction.getDirection())
                .lineName(direction.getLineName())
                .build();
    }
}
