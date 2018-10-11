package com.kotlarz.peka.proxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import spark.utils.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class PekaProxyService
{
    private static final String URL = "http://www.peka.poznan.pl/vm/method.vm";

    private static final String TS_PARAM = "ts";

    private static final String COMMAND_PARAM = "method";

    private static final String PATTERN_PARAM = "p0";

    private HttpClient client = HttpClientBuilder.create().build();

    private ObjectMapper mapper = new ObjectMapper();

    public String runCommand( String command, String pattern )
                    throws IOException
    {
        Assert.hasLength( command, "Command cannot be empty" );

        HttpPost post = buildPost( command, pattern );
        HttpResponse response = client.execute( post );
        return convertStreamToString( response.getEntity().getContent() );
    }

    private String convertStreamToString( InputStream stream )
    {
        Scanner scanner = new Scanner( stream ).useDelimiter( "\\A" );
        return scanner.hasNext() ? scanner.next() : "";
    }

    private HttpPost buildPost( String command, String pattern )
                    throws UnsupportedEncodingException, JsonProcessingException
    {
        HttpPost post = new HttpPost( buildUrl() );
        setHeaders( post );
        post.setEntity( new UrlEncodedFormEntity( buildParams( command, pattern ) ) );
        return post;
    }

    private void setHeaders( HttpPost post )
    {
        post.addHeader( "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8" );
    }

    private List<NameValuePair> buildParams( String command, String pattern )
                    throws JsonProcessingException
    {
        List<NameValuePair> params = new LinkedList<>();
        params.add( new BasicNameValuePair( COMMAND_PARAM, command ) );

        Map<String, String> patternMap = new HashMap<>();
        patternMap.put( "pattern", pattern );

        params.add( new BasicNameValuePair( PATTERN_PARAM, mapper.writeValueAsString( patternMap ) ) );
        return params;
    }

    private String buildUrl()
    {
        return URL + "?" + TS_PARAM + "=" + new Date().getTime();
    }
}
