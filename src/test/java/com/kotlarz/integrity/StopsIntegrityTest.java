package com.kotlarz.integrity;

import com.kotlarz.peka.adapter.service.PekaAdapterService;
import com.kotlarz.peka.proxy.service.PekaProxyService;
import com.kotlarz.peka.service.PekaService;
import com.kotlarz.transport.adapter.service.TransportAdapterService;
import com.kotlarz.transport.dto.TransportStop;
import com.kotlarz.transport.service.TransportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class StopsIntegrityTest {
    private PekaService pekaService;

    private TransportService transportService;

    @Before
    public void init() {
        PekaProxyService proxy = new PekaProxyService();
        PekaAdapterService adapter = new PekaAdapterService(proxy);
        pekaService = new PekaService(adapter);

        TransportAdapterService transportAdapterService = new TransportAdapterService();
        transportService = new TransportService(transportAdapterService);
    }

    @Test
    public void integrityTest() {
        List<TransportStop> allStops = transportService.getAllStops();
        List<String> emptyStops = allStops.parallelStream()
                .map(TransportStop::getName)
                .distinct()
                .filter(stopName -> pekaService.getBollards(stopName).isEmpty())
                .collect(Collectors.toList());

        System.out.println(emptyStops);
        Double factor = (double) emptyStops.size() / allStops.size() * 100;
        Assert.assertTrue("There are " + factor + "% of empty stops", factor < 1);
    }
}
