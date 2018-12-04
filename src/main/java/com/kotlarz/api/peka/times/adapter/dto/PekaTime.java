package com.kotlarz.api.peka.times.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class PekaTime
{
    private PekaBollardDetails bollard;

    private List<PekaTimeDetails> times;
}
