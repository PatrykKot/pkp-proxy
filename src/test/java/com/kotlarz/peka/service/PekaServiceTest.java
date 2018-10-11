package com.kotlarz.peka.service;

import com.kotlarz.peka.proxy.service.PekaProxyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PekaServiceTest
{
    private PekaService pekaService;

    @Before
    public void init()
    {
        pekaService = new PekaService( new PekaProxyService() );
    }

    @Test
    public void test()
    {
        boolean present = pekaService.getStopPointsByName( "szym" ).stream()
                        .anyMatch( stopPoint -> stopPoint.getName().toLowerCase().contains( "szym" ) );
        Assert.assertTrue( present );
    }
}