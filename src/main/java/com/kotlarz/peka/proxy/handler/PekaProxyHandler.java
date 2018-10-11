package com.kotlarz.peka.proxy.handler;

import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.peka.proxy.service.PekaProxyService;
import spark.Service;

import javax.inject.Inject;

public class PekaProxyHandler
                implements RequestHandler
{
    private PekaProxyService pekaProxyService;

    @Inject
    public PekaProxyHandler( PekaProxyService pekaProxyService )
    {
        this.pekaProxyService = pekaProxyService;
    }

    @Override
    public void register( Service service )
    {
        registerProxy( service );
    }

    private void registerProxy( Service service )
    {
        service.get( "proxy", ( request, response ) -> {
            String command = request.queryParams( "command" );
            String pattern = request.queryParamOrDefault( "pattern", "" );

            String content = pekaProxyService.runCommand( command, pattern );
            response.type( "application/json" );
            return content;
        } );
    }
}
