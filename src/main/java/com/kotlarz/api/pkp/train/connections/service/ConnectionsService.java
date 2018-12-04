package com.kotlarz.api.pkp.train.connections.service;

import com.kotlarz.api.pkp.train.connections.adapter.service.ConnectionsAdapterService;
import com.kotlarz.api.pkp.train.connections.dto.request.ConnectionSearchDto;
import com.kotlarz.api.pkp.train.connections.dto.response.ConnectionSearchResponseDto;
import com.kotlarz.api.pkp.train.stops.service.TrainStopsService;
import com.kotlarz.config.container.Bean;

import javax.inject.Inject;
import java.util.List;

@Bean
public class ConnectionsService
{
    private ConnectionsAdapterService adapterService;

    private TrainStopsService trainStopsService;

    @Inject
    public ConnectionsService( ConnectionsAdapterService adapterService,
                               TrainStopsService trainStopsService )
    {
        this.adapterService = adapterService;
        this.trainStopsService = trainStopsService;
    }

    public ConnectionSearchResponseDto find( ConnectionSearchDto searchDto )
    {
        ensureFromToStops( searchDto );
        return ConnectionSearchResponseDto.from( adapterService.find( searchDto.toAdapterDto() ) );
    }

    private void ensureFromToStops( ConnectionSearchDto searchDto )
    {
        List<String> fromStops = trainStopsService.getStops( searchDto.getFrom() );
        assert fromStops.size() == 1;

        List<String> toStops = trainStopsService.getStops( searchDto.getTo() );
        assert toStops.size() == 1;
    }
}
