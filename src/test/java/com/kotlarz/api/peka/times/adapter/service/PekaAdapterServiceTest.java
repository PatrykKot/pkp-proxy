package com.kotlarz.api.peka.times.adapter.service;

import com.kotlarz.api.peka.times.adapter.dto.PekaTime;
import com.kotlarz.api.peka.times.proxy.service.PekaProxyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PekaAdapterServiceTest
{
    private PekaAdapterService pekaAdapterService;

    @Before
    public void init()
    {
        pekaAdapterService = new PekaAdapterService( new PekaProxyService() );
    }

    @Test
    public void getStopPointsByNameTest()
    {
        boolean present = pekaAdapterService.getStopPointsByName( "szym" ).stream()
                        .anyMatch( stopPoint -> stopPoint.getName().toLowerCase().contains( "szym" ) );
        Assert.assertTrue( present );
    }

    @Test
    public void getBollardsByStopPointTest()
    {
        boolean present = pekaAdapterService.getBollardsByStopPoint( "Szymanowskiego" ).stream()
                        .anyMatch( bollard -> bollard.getBollard().getName().equals( "Szymanowskiego" ) );
        Assert.assertTrue( present );
    }

    @Test
    public void getTimesByBollardTest()
    {
        PekaTime time = pekaAdapterService.getTimesByBollard( "AWF03" );
        Assert.assertTrue( time.getBollard().getSymbol().equals( "AWF03" ) );
        Assert.assertFalse( time.getTimes().isEmpty() );
    }
}