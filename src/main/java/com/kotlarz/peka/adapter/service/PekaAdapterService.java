package com.kotlarz.peka.adapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kotlarz.config.container.Bean;
import com.kotlarz.peka.adapter.dto.Bollard;
import com.kotlarz.peka.adapter.dto.StopPoint;
import com.kotlarz.peka.adapter.dto.Time;
import com.kotlarz.peka.exception.PekaProxyException;
import com.kotlarz.peka.proxy.service.PekaProxyService;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Bean
public class PekaAdapterService {
    private final String GET_STOP_POINTS_COMMAND = "getStopPoints";

    private final String GET_STOP_POINTS_ARGUMENT_NAME = "pattern";

    private final String GET_BOLLARDS_BY_STOP_POINT_COMMAND = "getBollardsByStopPoint";

    private final String GET_BOLLARDS_BY_STOP_POINTS_ARGUMENT_NAME = "name";

    private final String GET_TIMES_BY_BOLLARD_COMMAND = "getTimes";

    private final String GET_TIMES_BY_BOLLARD_ARGUMENT_NAME = "symbol";

    private PekaProxyService pekaProxyService;

    private ObjectMapper mapper = new ObjectMapper();

    private LoadingCache<String, List<StopPoint>> stopPointsCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, List<StopPoint>>() {
                @Override
                public List<StopPoint> load(String name) {
                    return loadStopPointsByName(name);
                }
            });

    private LoadingCache<String, List<Bollard>> bollardsCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, List<Bollard>>() {
                @Override
                public List<Bollard> load(String stopPointName) {
                    return loadBollards(stopPointName);
                }
            });

    private LoadingCache<String, Time> timeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Time>() {
                @Override
                public Time load(String bollardTag) {
                    return loadTimes(bollardTag);
                }
            });

    @Inject
    public PekaAdapterService(PekaProxyService pekaProxyService) {
        this.pekaProxyService = pekaProxyService;
    }

    @SneakyThrows
    public List<StopPoint> getStopPointsByName(String name) {
        return stopPointsCache.get(name);
    }

    private List<StopPoint> loadStopPointsByName(String name) {
        JSONObject response = execute(GET_STOP_POINTS_COMMAND, GET_STOP_POINTS_ARGUMENT_NAME, name);
        return Arrays.asList(asType(response.getJSONArray("success"), StopPoint[].class));
    }

    @SneakyThrows
    public List<Bollard> getBollardsByStopPoint(String stopPointName) {
        return bollardsCache.get(stopPointName);
    }

    private List<Bollard> loadBollards(String stopPointName) {
        JSONObject response = execute(GET_BOLLARDS_BY_STOP_POINT_COMMAND, GET_BOLLARDS_BY_STOP_POINTS_ARGUMENT_NAME, stopPointName);
        JSONArray array = response.getJSONObject("success").getJSONArray("bollards");
        return Arrays.asList(asType(array, Bollard[].class));
    }

    @SneakyThrows
    public Time getTimesByBollard(String bollardTag) {
        return timeCache.get(bollardTag);
    }

    private Time loadTimes(String bollardTag) {
        JSONObject response = execute(GET_TIMES_BY_BOLLARD_COMMAND, GET_TIMES_BY_BOLLARD_ARGUMENT_NAME, bollardTag);
        return asType(response.getJSONObject("success"), Time.class);
    }

    @SneakyThrows
    private JSONObject execute(String command, String argumentName, String argument) {
        String response = pekaProxyService.runCommand(command, argumentName, argument);
        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.has("success")) {
            return jsonResponse;
        } else {
            throw new PekaProxyException(jsonResponse);
        }
    }

    @SneakyThrows
    private <T> T asType(JSONObject object, Class<T> clazz) {
        return mapper.readValue(object.toString(), clazz);
    }

    @SneakyThrows
    private <T> T asType(JSONArray array, Class<T> clazz) {
        return mapper.readValue(array.toString(), clazz);
    }
}
