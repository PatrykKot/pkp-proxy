package com.kotlarz.api.pkp.train.connections.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class ConnectionSearchResponseAdapterDto
{
    @JsonProperty( "P" )
    private List<TravelRouteAdapterDto> routes;
}
