package com.kotlarz.config.server;

import com.kotlarz.AppArguments;
import spark.Service;

public class ServerServiceInitializer
{
    public static Service start()
    {
        Service service = Service.ignite();
        initHttps( service );
        initBeforeFilter( service );
        return service;
    }

    private static void initHttps( Service service )
    {
        if ( AppArguments.KEYSTORE_PATH != null && AppArguments.KEYSTORE_PASSWORD != null )
        {
            service.port( 8443 );
            service.secure( AppArguments.KEYSTORE_PATH, AppArguments.KEYSTORE_PASSWORD, null, null );
        }
        else
        {
            service.port( 8080 );
        }
    }

    private static void initBeforeFilter( Service service )
    {
        service.before( ( request, response ) -> {
            response.header( "Access-Control-Allow-Origin", "*" );
            response.header( "Content-Encoding", "gzip" );
        } );
    }
}