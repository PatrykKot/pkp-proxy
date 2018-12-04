package com.kotlarz.api.peka.transport.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransportStopsByName
{
    private String name;

    private List<TransportStop> stops;
}
