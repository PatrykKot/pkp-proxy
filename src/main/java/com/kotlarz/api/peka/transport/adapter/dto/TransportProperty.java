package com.kotlarz.api.peka.transport.adapter.dto;

import lombok.Data;

@Data
public class TransportProperty
{
    private String zone;

    private String route_type;

    private String headsigns;

    private String stop_name;
}
