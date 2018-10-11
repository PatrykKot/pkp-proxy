package com.kotlarz.transport.dto;

import com.kotlarz.transport.adapter.dto.TransportFeature;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class TransportStop
{
    private String id;

    private String name;

    private String type; // TODO ENUM

    private Double latitude;

    private Double longitude;

    private String zone; // TODO ENUM;

    private String routeType; // TODO ENUM

    private List<String> transportLines;

    public static TransportStop from( TransportFeature transportFeature )
    {
        return TransportStop.builder()
                        .id( transportFeature.getId() )
                        .name( transportFeature.getProperties().getStop_name() )
                        .type( transportFeature.getType() )
                        .latitude( transportFeature.getGeometry().getCoordinates().get( 1 ) )
                        .longitude( transportFeature.getGeometry().getCoordinates().get( 0 ) )
                        .zone( transportFeature.getProperties().getZone() )
                        .routeType( transportFeature.getProperties().getRoute_type() )
                        .transportLines( toTransportLines( transportFeature.getProperties().getHeadsigns() ) )
                        .build();
    }

    private static List<String> toTransportLines( String headSigns )
    {
        return Stream.of( headSigns.split( "," ) )
                        .map( String::trim )
                        .map( String::toUpperCase )
                        .collect( Collectors.toList() );
    }
}
