package com.kotlarz.transport.handler;

import com.kotlarz.config.container.Bean;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.transport.service.TransportService;
import com.kotlarz.util.HandlerUtils;
import spark.Service;
import spark.utils.Assert;

import javax.inject.Inject;

@Bean
public class TransportStopHandler
        implements RequestHandler {
    private TransportService transportService;

    @Inject
    public TransportStopHandler(TransportService transportService) {
        this.transportService = transportService;
    }

    @Override
    public void register(Service service) {
        registerGetAllStops(service);
        registerGetClosestStops(service);
        registerGetClosestStopsNames(service);
    }

    private void registerGetAllStops(Service service) {
        service.get("stops", (request, response) -> {
            HandlerUtils.asJson(response);
            return HandlerUtils.toJson(transportService.getAllStops());
        });
    }

    private void registerGetClosestStops(Service service) {
        service.get("stops/closest", (request, response) -> {
            String longitude = request.queryParams("longitude");
            String latitude = request.queryParams("latitude");
            String limit = request.queryParams("limit");

            Assert.hasLength(longitude, "Longitude cannot be empty");
            Assert.hasLength(latitude, "Latitude cannot be empty");
            Assert.hasLength(limit, "Limit cannot be empty");

            HandlerUtils.asJson(response);
            return HandlerUtils.toJson(transportService.getClosest(Double.parseDouble(longitude),
                    Double.parseDouble(latitude),
                    Long.parseLong(limit)));
        });
    }

    private void registerGetClosestStopsNames(Service service) {
        service.get("stops/closest/names", (request, response) -> {
            String longitude = request.queryParams("longitude");
            String latitude = request.queryParams("latitude");
            String limit = request.queryParams("limit");

            Assert.hasLength(longitude, "Longitude cannot be empty");
            Assert.hasLength(latitude, "Latitude cannot be empty");
            Assert.hasLength(limit, "Limit cannot be empty");

            HandlerUtils.asJson(response);
            return HandlerUtils.toJson(transportService.getClosestNames(Double.parseDouble(longitude),
                    Double.parseDouble(latitude),
                    Long.parseLong(limit)));
        });
    }
}
