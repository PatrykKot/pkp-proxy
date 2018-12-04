package com.kotlarz.api.pkp.train.connections.service;

import com.kotlarz.api.pkp.train.connections.adapter.service.ConnectionsAdapterService;
import com.kotlarz.api.pkp.train.connections.dto.request.ConnectionSearchDto;
import com.kotlarz.api.pkp.train.connections.dto.response.ConnectionSearchResponseDto;
import com.kotlarz.api.pkp.train.stops.service.TrainStopsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionsServiceTest
{
    private ConnectionsService connectionsService;

    @Before
    public void init()
    {
        connectionsService = new ConnectionsService( new ConnectionsAdapterService(), new TrainStopsService() );
    }

    @Test
    public void find()
    {
        ConnectionSearchDto searchDto = ConnectionSearchDto.builder()
                        .from( "Warszawa Centralna" )
                        .to( "Poznań Główny" )
                        .build();
        ConnectionSearchResponseDto response = connectionsService.find( searchDto );

        Assert.assertTrue( !response.getRoutes().isEmpty() );
    }
}