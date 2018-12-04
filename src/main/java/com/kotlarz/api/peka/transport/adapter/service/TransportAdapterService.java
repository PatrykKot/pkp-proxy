package com.kotlarz.api.peka.transport.adapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlarz.api.peka.transport.adapter.dto.TransportFeature;
import com.kotlarz.api.peka.transport.adapter.dto.TransportResponse;
import com.kotlarz.config.container.Bean;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

@Bean
public class TransportAdapterService
{
    private static final String URL = "http://www.poznan.pl/mim/plan/map_service.html?mtype=pub_transport&co=cluster";

    private HttpClient client = HttpClientBuilder.create().build();

    private ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public List<TransportFeature> getTransportFeatures()
    {
        HttpGet get = new HttpGet( URL );
        HttpResponse response = client.execute( get );
        return mapper.readValue( response.getEntity().getContent(), TransportResponse.class ).getFeatures();
    }
}
