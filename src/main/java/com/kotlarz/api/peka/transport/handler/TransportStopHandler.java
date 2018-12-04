package com.kotlarz.api.peka.transport.handler;

import com.kotlarz.api.peka.transport.service.TransportService;
import com.kotlarz.config.container.Bean;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.util.HandlerUtils;
import spark.Service;
import spark.utils.Assert;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Bean
public class TransportStopHandler
                implements RequestHandler
{
    private TransportService transportService;

    @Inject
    public TransportStopHandler( TransportService transportService )
    {
        this.transportService = transportService;
    }

    @Override
    public void register( Service service )
    {
        registerGetAllStops( service );
        registerGetClosestStops( service );
        registerGetClosestStopsNames( service );
        registerGetClosestStopsGrouped( service );
        registerGetStopsByName( service );
    }

    private void registerGetAllStops( Service service )
    {
        service.get( "peka/stops", ( request, response ) -> {
            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.DAYS );
            return HandlerUtils.toJson( transportService.getAllStops() );
        } );
    }

    private void registerGetClosestStops( Service service )
    {
        service.get( "peka/stops/closest", ( request, response ) -> {
            String longitude = request.queryParams( "longitude" );
            String latitude = request.queryParams( "latitude" );
            String limit = request.queryParams( "limit" );

            Assert.hasLength( longitude, "Longitude cannot be empty" );
            Assert.hasLength( latitude, "Latitude cannot be empty" );
            Assert.hasLength( limit, "Limit cannot be empty" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.DAYS );
            return HandlerUtils.toJson
                            (
                                            transportService.getClosest(
                                                            Double.parseDouble( longitude ),
                                                            Double.parseDouble( latitude ),
                                                            Long.parseLong( limit )
                                            )
                            );
        } );
    }

    private void registerGetClosestStopsNames( Service service )
    {
        service.get( "peka/stops/closest/names", ( request, response ) -> {
            String longitude = request.queryParams( "longitude" );
            String latitude = request.queryParams( "latitude" );
            String limit = request.queryParams( "limit" );

            Assert.hasLength( longitude, "Longitude cannot be empty" );
            Assert.hasLength( latitude, "Latitude cannot be empty" );
            Assert.hasLength( limit, "Limit cannot be empty" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.DAYS );
            return HandlerUtils.toJson
                            (
                                            transportService.getClosestNames
                                                            (
                                                                            Double.parseDouble( longitude ),
                                                                            Double.parseDouble( latitude ),
                                                                            Long.parseLong( limit )
                                                            )
                            );
        } );
    }

    private void registerGetClosestStopsGrouped( Service service )
    {
        service.get( "peka/stops/closest/grouped", ( request, response ) -> {
            String longitude = request.queryParams( "longitude" );
            String latitude = request.queryParams( "latitude" );
            String limit = request.queryParams( "limit" );

            Assert.hasLength( longitude, "Longitude cannot be empty" );
            Assert.hasLength( latitude, "Latitude cannot be empty" );
            Assert.hasLength( limit, "Limit cannot be empty" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.DAYS );
            return HandlerUtils.toJson
                            (
                                            transportService.getClosestGrouped
                                                            (
                                                                            Double.parseDouble( longitude ),
                                                                            Double.parseDouble( latitude ),
                                                                            Long.parseLong( limit )
                                                            )
                            );
        } );
    }

    private void registerGetStopsByName( Service service )
    {
        service.get( "peka/stops/search", ( request, response ) -> {
            String name = request.queryParamOrDefault( "name", "" );
            String start = request.queryParams( "start" );
            String limit = request.queryParams( "limit" );

            Assert.hasLength( start, "Start cannot be empty" );
            Assert.hasLength( limit, "Limit cannot be empty" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.DAYS );

            return HandlerUtils.toJson
                            (
                                            transportService.getByName
                                                            (
                                                                            name,
                                                                            Integer.parseInt( start ),
                                                                            Integer.parseInt( limit )
                                                            )
                            );
        } );
    }
}
