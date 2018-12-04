package com.kotlarz.api.pkp.train.stops.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TrainStopsServiceTest
{
    private TrainStopsService trainStopsService;

    @Before
    public void init()
    {
        trainStopsService = new TrainStopsService();
    }

    @Test
    public void warsFindTest()
    {
        String testQuery = "wars";

        List<String> stops = trainStopsService.getStops( testQuery );
        Assert.assertTrue( !stops.isEmpty() );

        stops.forEach( stopName -> Assert.assertTrue( stopName.toLowerCase().contains( testQuery ) ) );
    }
}