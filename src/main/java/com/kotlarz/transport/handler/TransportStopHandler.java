package com.kotlarz.transport.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.transport.service.TransportService;
import spark.Response;
import spark.Service;
import spark.utils.Assert;

import javax.inject.Inject;

public class TransportStopHandler
                implements RequestHandler
{
    private TransportService transportService;

    private ObjectMapper mapper = new ObjectMapper();

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
    }

    private void registerGetAllStops( Service service )
    {
        service.get( "stops", ( request, response ) -> {
            asJson( response );
            return toJson( transportService.getAllStops() );
        } );
    }

    private void registerGetClosestStops( Service service )
    {
        service.get( "stops/closest", ( request, response ) -> {
            String longitude = request.queryParams( "lon" );
            String latitude = request.queryParams( "lat" );
            String limit = request.queryParams( "limit" );

            Assert.hasLength( longitude, "Longitude cannot be empty" );
            Assert.hasLength( latitude, "Latidude cannot be empty" );
            Assert.hasLength( limit, "Limit cannot be empty" );

            asJson( response );
            return toJson( transportService.getClosest( Double.parseDouble( longitude ),
                                                        Double.parseDouble( latitude ),
                                                        Long.parseLong( limit ) ) );
        } );
    }

    private void asJson( Response response )
    {
        response.type( "application/json" );
    }

    private String toJson( Object object )
                    throws JsonProcessingException
    {
        return mapper.writeValueAsString( object );
    }
}
