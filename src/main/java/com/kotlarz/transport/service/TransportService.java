package com.kotlarz.transport.service;

import com.google.common.base.Suppliers;
import com.kotlarz.config.container.Bean;
import com.kotlarz.transport.adapter.service.TransportAdapterService;
import com.kotlarz.transport.dto.TransportStop;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Bean
public class TransportService
{
    private TransportAdapterService transportAdapterService;

    private Supplier<List<TransportStop>> stopsSupplier =
            Suppliers.memoizeWithExpiration( () -> loadStops(), 1, TimeUnit.DAYS );

    @Inject
    public TransportService(TransportAdapterService transportAdapterService ) {
        this.transportAdapterService = transportAdapterService;
    }

    public List<TransportStop> getAllStops() {
        return stopsSupplier.get();
    }

    private List<TransportStop> loadStops() {
        return transportAdapterService.getTransportFeatures().stream()
                .map( TransportStop::from )
                .collect( Collectors.toList() );
    }

    public List<TransportStop> getClosest( Double longitude, Double latitude, Long limit ) {
        return getAllStops().stream()
                .sorted( ( stop1, stop2 ) -> {
                    Double distance1 = getDistance( longitude, latitude, stop1 );
                    Double distance2 = getDistance( longitude, latitude, stop2 );
                    return distance1.compareTo( distance2 );
                } )
                .limit( limit )
                .collect( Collectors.toList() );
    }

    private Double getDistance( Double longitude, Double latitude, TransportStop transportStop ) {
        Double longDiff = longitude - transportStop.getLongitude();
        Double latDiff = latitude - transportStop.getLatitude();

        return Math.sqrt( longDiff * longDiff + latDiff * latDiff );
    }
}
