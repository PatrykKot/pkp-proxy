package com.kotlarz.transport.service;

import com.kotlarz.transport.adapter.service.TransportAdapterService;
import com.kotlarz.transport.dto.TransportStop;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TransportServiceTest {
    private TransportService transportService;

    @Before
    public void init() {
        TransportAdapterService transportAdapterService = new TransportAdapterService();
        transportService = new TransportService(transportAdapterService);
    }

    @Test
    public void getAllStopsTest() {
        List<TransportStop> allStops = transportService.getAllStops();
        Assert.assertFalse(allStops.isEmpty());
    }
}