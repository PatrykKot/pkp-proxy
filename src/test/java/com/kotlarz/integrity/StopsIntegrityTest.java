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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
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
    public void integrityTest() throws ExecutionException, InterruptedException {
        List<TransportStop> allStops = transportService.getAllStops();

        ExecutorService executor = Executors.newFixedThreadPool(allStops.size() / 10);
        List<Future<String>> futures = allStops.stream()
                .map(TransportStop::getName)
                .distinct()
                .map(name -> executor.submit(() -> {
                    System.out.println("Downloading bollards for stop " + name);
                    boolean noBollards = pekaService.getBollards(name).isEmpty();
                    if (noBollards) {
                        return name;
                    } else {
                        return null;
                    }
                }))
                .collect(Collectors.toList());

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        List<String> emptyStops = new LinkedList<>();
        for (Future<String> future : futures) {
            String name = future.get();
            if (name != null) {
                emptyStops.add(name);
            }
        }

        System.out.println(emptyStops);
        Double factor = (double) emptyStops.size() / allStops.size() * 100;
        Assert.assertTrue("There are " + factor + "% of empty stops", factor < 1);
    }
}
