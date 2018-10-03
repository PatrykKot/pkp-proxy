package com.kotlarz.server;

import com.kotlarz.AppArguments;
import com.kotlarz.service.PekaService;
import spark.Service;

public class Server
{
    public static void start()
    {
        PekaService pekaService = new PekaService();
        Service service = Service.ignite();

        prepareService( service );
        prepareRequests( pekaService, service );
    }

    private static void prepareRequests( PekaService pekaService, Service service )
    {
        service.get( "/", ( request, response ) -> {
            String command = request.queryParams( "command" );
            String pattern = request.queryParamOrDefault( "pattern", "" );

            String content = pekaService.runCommand( command, pattern );
            response.type( "application/json" );
            return content;
        } );
    }

    private static void prepareService( Service service )
    {
        service.port( 8443 );
        service.secure( AppArguments.KEYSTORE_PATH, AppArguments.KEYSTORE_PASSWORD, null, null );
        service.before( ( request, response ) -> {
            response.header( "Access-Control-Allow-Origin", "*" );
            response.header( "Content-Encoding", "gzip" );
        } );
    }
}