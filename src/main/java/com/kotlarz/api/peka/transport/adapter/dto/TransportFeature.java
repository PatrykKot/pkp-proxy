package com.kotlarz.api.peka.transport.adapter.dto;

import lombok.Data;

@Data
public class TransportFeature
{
    private TransportGeometry geometry;

    private String id;

    private String type;

    private TransportProperty properties;
}