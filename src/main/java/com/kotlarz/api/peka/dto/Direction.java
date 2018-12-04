package com.kotlarz.api.peka.dto;

import com.kotlarz.api.peka.times.adapter.dto.PekaDirection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Direction
{
    private Boolean returnVariant;

    private String direction;

    private String lineName;

    public static Direction from( PekaDirection direction )
    {
        return Direction.builder()
                        .returnVariant( direction.getReturnVariant() )
                        .direction( direction.getDirection() )
                        .lineName( direction.getLineName() )
                        .build();
    }
}
