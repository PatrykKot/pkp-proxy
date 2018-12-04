package com.kotlarz.api.pkp.train.connections.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class TravelRouteAdapterDto
{
    @JsonProperty( "C" )
    private String travelTime;

    @JsonProperty( "KMK" )
    private Double kilometers;

    @JsonProperty( "L" )
    private Integer transfersCount;

    @JsonProperty( "N" )
    private List<TrainRouteAdapterDto> trainRouteAdapterDtos;

    @JsonProperty( "O" )
    private String travelStartDate;

    @JsonProperty( "P" )
    private String travelFinishDate;
}
