package com.kotlarz.config.container;

import com.google.inject.AbstractModule;
import com.kotlarz.peka.proxy.handler.PekaProxyHandler;
import com.kotlarz.peka.proxy.service.PekaProxyService;
import com.kotlarz.transport.adapter.service.TransportAdapterService;
import com.kotlarz.transport.handler.TransportStopHandler;
import com.kotlarz.transport.service.TransportService;

public class GuiceModule
                extends AbstractModule
{
    private final Class<?>[] classes = {
                    PekaProxyService.class,
                    PekaProxyHandler.class,
                    TransportAdapterService.class,
                    TransportService.class,
                    TransportStopHandler.class,
                    PekaProxyService.class
    };

    @Override
    protected void configure()
    {
        for ( Class<?> clazz : classes )
        {
            bind( clazz );
        }
    }
}
