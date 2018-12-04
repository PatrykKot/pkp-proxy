package com.kotlarz.api.pkp.train.stops.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlarz.api.pkp.constraints.Constraints;
import com.kotlarz.config.container.Bean;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.util.List;

@Bean
public class TrainStopsService
{
    private static final String PATH = "/PhoneGap_1/PhoneGapService.svc/S";

    private HttpClient client = HttpClientBuilder.create().build();

    private ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public List<String> getStops( String query )
    {
        URI uri = new URIBuilder()
                        .setScheme( "http" )
                        .setHost( Constraints.PKP_HOST )
                        .setPath( PATH )
                        .setParameter( "q", query )
                        .build();

        HttpGet get = new HttpGet( uri );
        HttpResponse response = client.execute( get );
        return mapper.readValue( response.getEntity().getContent(), new TypeReference<List<String>>()
        {
        } );
    }
}
