package com.kotlarz.api.peka.transport.service;

import com.google.common.base.Suppliers;
import com.kotlarz.api.peka.transport.adapter.service.TransportAdapterService;
import com.kotlarz.api.peka.transport.dto.TransportStop;
import com.kotlarz.api.peka.transport.dto.TransportStopsByName;
import com.kotlarz.config.container.Bean;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Bean
public class TransportService
{
    private TransportAdapterService transportAdapterService;

    private Supplier<List<TransportStop>> stopsSupplier =
                    Suppliers.memoizeWithExpiration( this::loadStops, 1, TimeUnit.DAYS );

    @Inject
    public TransportService( TransportAdapterService transportAdapterService )
    {
        this.transportAdapterService = transportAdapterService;
    }

    public List<TransportStop> getAllStops()
    {
        return stopsSupplier.get();
    }

    private List<TransportStop> loadStops()
    {
        return transportAdapterService.getTransportFeatures().stream()
                        .map( TransportStop::from )
                        .collect( Collectors.toList() );
    }

    public List<TransportStop> getClosest( Double longitude, Double latitude, Long limit )
    {
        return getAllStops().stream()
                        .sorted( distanceComparator( longitude, latitude ) )
                        .limit( limit )
                        .collect( Collectors.toList() );
    }

    public List<String> getClosestNames( Double longitude, Double latitude, long limit )
    {
        return getAllStops().stream()
                        .sorted( distanceComparator( longitude, latitude ) )
                        .map( TransportStop::getName )
                        .distinct()
                        .limit( limit )
                        .collect( Collectors.toList() );
    }

    public List<TransportStopsByName> getClosestGrouped( Double longitude, Double latitude, long limit )
    {
        List<TransportStop> allStops = getAllStops();
        return getClosestNames( longitude, latitude, limit ).stream()
                        .map( name -> TransportStopsByName.builder()
                                        .name( name )
                                        .stops( allStops.stream()
                                                                .filter( transportStop -> transportStop.getName()
                                                                                .equals( name ) )
                                                                .collect( Collectors.toList() ) )
                                        .build() )
                        .collect( Collectors.toList() );
    }

    private Comparator<TransportStop> distanceComparator( Double longitude, Double latitude )
    {
        return ( stop1, stop2 ) -> {
            Double distance1 = getDistance( longitude, latitude, stop1 );
            Double distance2 = getDistance( longitude, latitude, stop2 );
            return distance1.compareTo( distance2 );
        };
    }

    private Double getDistance( Double longitude, Double latitude, TransportStop transportStop )
    {
        Double longDiff = longitude - transportStop.getLongitude();
        Double latDiff = latitude - transportStop.getLatitude();

        return Math.sqrt( longDiff * longDiff + latDiff * latDiff );
    }

    public List<TransportStopsByName> getByName( String rawName, Integer start, Integer limit )
    {
        String name = rawName.trim().toLowerCase();

        List<TransportStop> allStops = getAllStops();
        List<TransportStopsByName> stops = allStops.stream()
                        .filter( transportStop -> transportStop.getName().toLowerCase().contains( name ) )
                        .map( TransportStop::getName )
                        .distinct()
                        .sorted()
                        .map( matchingName -> TransportStopsByName.builder()
                                        .name( matchingName )
                                        .stops( allStops.stream()
                                                                .filter( stop -> stop.getName().equals( matchingName ) )
                                                                .collect( Collectors.toList() ) )
                                        .build() )
                        .collect( Collectors.toList() );

        Integer toIndex = start + limit;
        if ( toIndex > stops.size() )
        {
            toIndex = stops.size();
        }
        return stops.subList( start, toIndex );
    }
}
