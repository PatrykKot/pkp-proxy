package com.kotlarz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Response;

import java.util.concurrent.TimeUnit;

public class HandlerUtils
{
    private static ObjectMapper mapper = new ObjectMapper();

    public static void asJson( Response response )
    {
        response.type( "application/json" );
    }

    public static String toJson( Object object )
                    throws JsonProcessingException
    {
        return mapper.writeValueAsString( object );
    }

    public static void cache( Response response, Integer cacheTime, TimeUnit unit )
    {
        response.header( "Cache-Control", "max-age=" + unit.toSeconds( cacheTime ) );
    }
}
