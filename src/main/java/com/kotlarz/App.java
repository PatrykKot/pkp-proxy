package com.kotlarz;

import com.kotlarz.service.PekaService;
import spark.Service;

public class App
{
    private static PekaService pekaService = new PekaService();

    public static void main( String[] args )
    {
        Service service = Service.ignite();
        service.port( 8888 );

        service.before( ( request, response ) -> {
            response.header( "Access-Control-Allow-Origin", "*" );
            response.header( "Content-Encoding", "gzip" );
        } );

        service.get( "/", ( request, response ) -> {
            String command = request.queryParams( "command" );
            String pattern = request.queryParamOrDefault( "pattern", "" );

            String content = pekaService.runCommand( command, pattern );
            response.type( "application/json" );
            return content;
        } );
    }
}
