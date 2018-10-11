package com.kotlarz.peka.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class Bollard
{
    private List<Direction> directions;

    private BollardDetails bollard;
}
