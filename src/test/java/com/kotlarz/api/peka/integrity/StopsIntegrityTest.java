package com.kotlarz.api.peka.integrity;

import com.kotlarz.api.peka.times.adapter.service.PekaAdapterService;
import com.kotlarz.api.peka.times.proxy.service.PekaProxyService;
import com.kotlarz.api.peka.times.service.PekaService;
import com.kotlarz.api.peka.transport.adapter.service.TransportAdapterService;
import com.kotlarz.api.peka.transport.dto.TransportStop;
import com.kotlarz.api.peka.transport.service.TransportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class StopsIntegrityTest
{
    private PekaService pekaService;

    private TransportService transportService;

    @Before
    public void init()
    {
        PekaProxyService proxy = new PekaProxyService();
        PekaAdapterService adapter = new PekaAdapterService( proxy );
        pekaService = new PekaService( adapter );

        TransportAdapterService transportAdapterService = new TransportAdapterService();
        transportService = new TransportService( transportAdapterService );
    }

    @Test
    public void integrityTest()
                    throws ExecutionException, InterruptedException
    {
        List<TransportStop> allStops = transportService.getAllStops();

        ExecutorService executor = Executors.newFixedThreadPool( allStops.size() / 10 );
        List<Future<String>> futures = allStops.stream()
                        .map( TransportStop::getName )
                        .distinct()
                        .map( name -> executor.submit( () -> {
                            System.out.println( "Downloading bollards for stop " + name );
                            boolean noBollards = pekaService.getBollards( name ).isEmpty();
                            if ( noBollards )
                            {
                                return name;
                            }
                            else
                            {
                                return null;
                            }
                        } ) )
                        .collect( Collectors.toList() );

        executor.shutdown();
        executor.awaitTermination( 10, TimeUnit.MINUTES );

        List<String> emptyStops = new LinkedList<>();
        for ( Future<String> future : futures )
        {
            String name = future.get();
            if ( name != null )
            {
                emptyStops.add( name );
            }
        }

        System.out.println( emptyStops );
        Double factor = (double) emptyStops.size() / allStops.size() * 100;
        Assert.assertTrue( "There are " + factor + "% of empty stops", factor < 1 );
    }
}
