package com.kotlarz.config.filter;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import lombok.SneakyThrows;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EtagFilter
                implements Filter
{
    private static final String ETAG_HEADER = "ETag";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String IF_NONE_MATCH_HEADER = "If-None-Match";

    private static final String[] CONTENT_TYPES_TO_RESOLVE = { "application/json" };

    @Override
    public void handle( Request wrappedRequest, Response wrappedResponse )
    {
        if ( shouldFilter( wrappedResponse ) )
        {
            String body = wrappedResponse.body();
            String hash = calculateHash( body );
            wrappedResponse.header( ETAG_HEADER, hash );

            boolean hashMatches = hasHash( wrappedRequest, hash );
            if ( hashMatches )
            {
                wrappedResponse.status( 304 );
                wrappedResponse.body( "" );
            }
        }
    }

    private boolean shouldFilter( Response wrappedResponse )
    {
        HttpServletResponse response = wrappedResponse.raw();
        String contentType = response.getHeader( CONTENT_TYPE_HEADER );
        return Arrays.asList( CONTENT_TYPES_TO_RESOLVE ).contains( contentType );
    }

    @SneakyThrows
    private String calculateHash( String text )
    {
        HashCode hashCode = Hashing.md5().hashString( text, StandardCharsets.UTF_8 );
        return hashCode.toString();
    }

    private boolean hasHash( Request wrappedRequest, String hash )
    {
        return getHashes( wrappedRequest ).stream()
                        .anyMatch( requestHash -> requestHash.equals( hash ) || requestHash.equals( "*" ) );
    }

    private List<String> getHashes( Request wrappedRequest )
    {
        String header = wrappedRequest.headers( IF_NONE_MATCH_HEADER );
        if ( StringUtils.isBlank( header ) )
        {
            return new LinkedList<>();
        }
        else
        {
            return Stream.of( header.split( "," ) )
                            .peek( String::trim )
                            .map( hash -> hash.replaceFirst( "^\\w*\"", "" ) )
                            .map( hash -> hash.replaceFirst( "\"$", "" ) )
                            .collect( Collectors.toList() );
        }
    }
}
