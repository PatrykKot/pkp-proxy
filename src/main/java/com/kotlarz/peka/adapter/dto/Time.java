package com.kotlarz.peka.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class Time
{
    private BollardDetails bollard;

    private List<TimeDetails> times;
}
