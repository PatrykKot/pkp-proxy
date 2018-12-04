package com.kotlarz.api.pkp.train.connections.adapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlarz.api.pkp.constraints.Constraints;
import com.kotlarz.api.pkp.train.connections.adapter.dto.request.ConnectionSearchAdapterDto;
import com.kotlarz.api.pkp.train.connections.adapter.dto.response.ConnectionSearchResponseAdapterDto;
import com.kotlarz.config.container.Bean;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.net.URI;

@Bean
public class ConnectionsAdapterService
{
    private static final String PATH = "/PhoneGap_1/PhoneGapService.svc/C";

    private HttpClient client = HttpClientBuilder.create().build();

    private ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public ConnectionSearchResponseAdapterDto find( ConnectionSearchAdapterDto searchAdapterDto )
    {
        URI uri = new URIBuilder()
                        .setScheme( "http" )
                        .setHost( Constraints.PKP_HOST )
                        .setPath( PATH )
                        .build();

        HttpPost post = new HttpPost( uri );

        String requestDto = mapper.writeValueAsString( searchAdapterDto );
        post.setEntity( new StringEntity( requestDto, ContentType.APPLICATION_JSON ) );

        HttpResponse response = client.execute( post );
        assert response.getStatusLine().getStatusCode() == 200;

        InputStream contentStream = response.getEntity().getContent();
        return mapper.readValue( contentStream, ConnectionSearchResponseAdapterDto.class );
    }
}