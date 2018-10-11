package com.kotlarz.peka.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class PekaTime {
    private PekaBollardDetails bollard;

    private List<PekaTimeDetails> times;
}
