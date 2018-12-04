package com.kotlarz.api.peka.times.service;

import com.kotlarz.api.peka.dto.Time;
import com.kotlarz.api.peka.times.adapter.service.PekaAdapterService;
import com.kotlarz.api.peka.times.proxy.service.PekaProxyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PekaServiceTest
{
    private PekaService pekaService;

    @Before
    public void init()
    {
        PekaProxyService proxy = new PekaProxyService();
        PekaAdapterService adapter = new PekaAdapterService( proxy );
        pekaService = new PekaService( adapter );
    }

    @Test
    public void getStopsTest()
    {
        boolean contains = pekaService.getStops( "szym" ).stream()
                        .anyMatch( stop -> stop.getName().toLowerCase().contains( "szym" ) );
        Assert.assertTrue( contains );
    }

    @Test
    public void getBollardsTest()
    {
        boolean contains = pekaService.getBollards( "Szymanowskiego" ).stream()
                        .anyMatch( bollard -> bollard.getName().equals( "Szymanowskiego" ) );
        Assert.assertTrue( contains );
    }

    @Test
    public void getTimes()
    {
        List<Time> times = pekaService.getTimes( "AWF03" );
        Assert.assertFalse( times.isEmpty() );
    }
}