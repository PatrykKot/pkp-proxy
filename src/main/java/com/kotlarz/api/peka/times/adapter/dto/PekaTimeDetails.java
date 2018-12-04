package com.kotlarz.api.peka.times.adapter.dto;

import lombok.Data;

@Data
public class PekaTimeDetails
{
    private Boolean realTime;

    private Long minutes;

    private String direction;

    private Boolean onStopPoint;

    private String departure;

    private String line;
}
