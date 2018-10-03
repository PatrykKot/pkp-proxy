package com.kotlarz.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import spark.utils.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class PekaService
{
    private static final String URL = "http://www.peka.poznan.pl/vm/method.vm";

    private static final String TS_PARAM = "ts";

    private static final String COMMAND_PARAM = "method";

    private static final String PATTERN_PARAM = "p0";

    private HttpClient client = HttpClientBuilder.create().build();

    public String runCommand( String command, String pattern )
                    throws IOException
    {
        Assert.hasLength( command, "Command cannot be empty" );

        HttpPost post = buildPost( command, pattern );
        HttpResponse response = client.execute( post );
        return convertStreamToString( response.getEntity().getContent() );
    }

    String convertStreamToString( InputStream stream )
    {
        Scanner s = new Scanner( stream ).useDelimiter( "\\A" );
        return s.hasNext() ? s.next() : "";
    }

    private HttpPost buildPost( String command, String pattern )
                    throws UnsupportedEncodingException
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
    {
        List<NameValuePair> params = new LinkedList<>();
        params.add( new BasicNameValuePair( COMMAND_PARAM, command ) );

        Map<String, String> patternMap = new HashMap<>();
        patternMap.put( PATTERN_PARAM, pattern );

        params.add( new BasicNameValuePair( PATTERN_PARAM, new JSONObject( patternMap ).toString() ) );
        return params;
    }

    private String buildUrl()
    {
        return URL + "?" + TS_PARAM + "=" + new Date().getTime();
    }
}
