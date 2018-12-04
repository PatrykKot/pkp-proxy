package com.kotlarz.api.pkp.train.connections.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kotlarz.api.pkp.train.enums.Carrier;
import com.kotlarz.api.pkp.train.enums.TrainType;
import com.kotlarz.api.pkp.train.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConnectionSearchAdapterDto
{
    @JsonProperty( "C" )
    private Integer c;

    @JsonProperty( "N" )
    private Integer n;

    @JsonProperty( "S" )
    private String startingStop;

    @JsonProperty( "T" )
    private String targetStop;

    @JsonProperty( "V" )
    private String v;

    @JsonProperty( "O" )
    private Boolean isDepartureTime;

    @JsonProperty( "B" )
    private Boolean onlyDirectConnections;

    @JsonProperty( "CP" )
    private Integer minimumTransferTimeMin;

    @JsonProperty( "D" )
    private String searchDate;

    @JsonProperty( "TS" )
    private List<String> trainTypes;

    @JsonProperty( "CS" )
    private List<String> carriers;

    @JsonProperty( "PS" )
    private List<String> additionalStops;

    @JsonProperty( "JK" )
    private String jk;

    public static ConnectionSearchAdapterDto buildDefault()
    {
        List<String> trainTypeParams = Stream.of( TrainType.values() )
                        .map( TrainType::getId )
                        .collect( Collectors.toList() );
        List<String> carrierTypeParams = Stream.of( Carrier.values() )
                        .map( Carrier::getId )
                        .collect( Collectors.toList() );

        return ConnectionSearchAdapterDto.builder()
                        .c( 5 )
                        .n( 0 )
                        .startingStop( "" )
                        .targetStop( "" )
                        .v( "" )
                        .isDepartureTime( true )
                        .onlyDirectConnections( false )
                        .minimumTransferTimeMin( 10 )
                        .searchDate( DateUtil.toString( new Date() ) )
                        .trainTypes( trainTypeParams )
                        .carriers( carrierTypeParams )
                        .additionalStops( Arrays.asList() )
                        .jk( "" )
                        .build();
    }
}
