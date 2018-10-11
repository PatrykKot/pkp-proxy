package com.kotlarz.transport.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties( value = { "crs", "type" } )
public class TransportResponse
{
    private List<TransportFeature> features;
}
