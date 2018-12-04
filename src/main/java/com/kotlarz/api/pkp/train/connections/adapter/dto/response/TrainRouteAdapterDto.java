package com.kotlarz.api.pkp.train.connections.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class TrainRouteAdapterDto
{
    @JsonProperty( "D" )
    private String trainName;

    @JsonProperty( "KMK" )
    private String kilometers;

    @JsonProperty( "N" )
    private String trainId;

    @JsonProperty( "P" )
    private String carrierId;

    @JsonProperty( "S" )
    private String trainTypeId;
}
