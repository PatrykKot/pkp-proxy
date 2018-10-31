package com.kotlarz.peka.handler;

import com.kotlarz.config.container.Bean;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.peka.service.PekaService;
import com.kotlarz.util.HandlerUtils;
import spark.Service;
import spark.utils.Assert;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Bean
public class PekaHandler
                implements RequestHandler
{
    private PekaService pekaService;

    @Inject
    public PekaHandler( PekaService pekaService )
    {
        this.pekaService = pekaService;
    }

    @Override
    public void register( Service service )
    {
        registerGetBollards( service );
        registerGetTimes( service );
    }

    private void registerGetTimes( Service service )
    {
        service.get( "times", ( request, response ) -> {
            String bollardTag = request.queryParams( "bollardTag" );
            Assert.hasLength( bollardTag, "Bollard tag cannot be empty" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.SECONDS );
            return HandlerUtils.toJson( pekaService.getTimes( bollardTag ) );
        } );
    }

    private void registerGetBollards( Service service )
    {
        service.get( "bollards", ( request, response ) -> {
            String stopName = request.queryParams( "stopName" );
            Assert.hasLength( stopName, "Stop name cannot be empty" );

            HandlerUtils.asJson( response );
            HandlerUtils.cache( response, 1, TimeUnit.DAYS );
            return HandlerUtils.toJson( pekaService.getBollards( stopName ) );
        } );
    }
}
