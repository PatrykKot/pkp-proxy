package com.kotlarz.api.peka.transport.adapter.service;

import com.kotlarz.api.peka.transport.adapter.dto.TransportFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TransportAdapterServiceTest
{
    private TransportAdapterService transportAdapterService;

    @Before
    public void init()
    {
        transportAdapterService = new TransportAdapterService();
    }

    @Test
    public void getTransportFeaturesTest()
    {
        List<TransportFeature> features = transportAdapterService.getTransportFeatures();
        Assert.assertFalse( features.isEmpty() );
    }
}