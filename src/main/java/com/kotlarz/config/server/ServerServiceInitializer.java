package com.kotlarz.config.server;

import com.kotlarz.AppArguments;
import com.kotlarz.config.filter.CorsFilter;
import com.kotlarz.config.filter.EtagFilter;
import com.kotlarz.config.filter.GzipFilter;
import lombok.extern.slf4j.Slf4j;
import spark.Filter;
import spark.Service;

@Slf4j
public class ServerServiceInitializer
{
    private static final Filter[] BEFORE_FILTERS = { new GzipFilter(), new CorsFilter() };

    private static final Filter[] AFTER_FILTERS = { new EtagFilter() };

    public static Service start()
    {
        Service service = Service.ignite();
        initHttps( service );
        initBeforeFilters( service );
        initAfterFilters( service );
        return service;
    }

    private static void initHttps( Service service )
    {
        if ( AppArguments.KEYSTORE_PATH != null && AppArguments.KEYSTORE_PASSWORD != null )
        {
            log.info( "Enabling HTTPS" );
            service.port( AppArguments.PORT );
            service.secure( AppArguments.KEYSTORE_PATH, AppArguments.KEYSTORE_PASSWORD, null, null );
        }
        else
        {
            log.info( "Enabling HTTP" );
            service.port( AppArguments.PORT );
        }
    }

    private static void initBeforeFilters( Service service )
    {
        for ( Filter filter : BEFORE_FILTERS )
        {
            log.info( "Registering before filter {}", filter.getClass().getName() );
            service.before( filter );
        }
    }

    private static void initAfterFilters( Service service )
    {
        for ( Filter filter : AFTER_FILTERS )
        {
            log.info( "Registering after filter {}", filter.getClass().getName() );
            service.after( filter );
        }
    }
}