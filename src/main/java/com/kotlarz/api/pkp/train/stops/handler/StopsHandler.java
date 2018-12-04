package com.kotlarz.api.pkp.train.stops.handler;

import com.kotlarz.api.pkp.train.stops.service.TrainStopsService;
import com.kotlarz.config.container.Bean;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.util.HandlerUtils;
import spark.Service;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Bean
public class StopsHandler
                implements RequestHandler
{
    private TrainStopsService trainStopsService;

    @Inject
    public StopsHandler( TrainStopsService trainStopsService )
    {
        this.trainStopsService = trainStopsService;
    }

    @Override
    public void register( Service service )
    {
        registerGetQueryStops( service );
    }

    private void registerGetQueryStops( Service service )
    {
        service.get( "pkp/stops", ( request, response ) -> {
            String query = request.queryParamOrDefault( "query", "" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 7, TimeUnit.DAYS );
            return HandlerUtils.toJson( trainStopsService.getStops( query ) );
        } );
    }
}
