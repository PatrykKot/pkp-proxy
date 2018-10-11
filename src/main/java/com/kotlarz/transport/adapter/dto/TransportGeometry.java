package com.kotlarz.transport.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransportGeometry
{
    private List<Double> coordinates;

    private String type;
}
