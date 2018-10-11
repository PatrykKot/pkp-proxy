package com.kotlarz.peka.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class PekaBollard {
    private List<PekaDirection> directions;

    private PekaBollardDetails bollard;
}
