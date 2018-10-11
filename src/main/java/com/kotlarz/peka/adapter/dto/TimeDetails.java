package com.kotlarz.peka.adapter.dto;

import lombok.Data;

@Data
public class TimeDetails
{
    private Boolean realTime;

    private Long minutes;

    private String direction;

    private Boolean onStopPoint;

    private String departure;

    private String line;
}
