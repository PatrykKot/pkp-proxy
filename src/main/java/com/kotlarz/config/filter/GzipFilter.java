package com.kotlarz.config.filter;

import spark.Filter;
import spark.Request;
import spark.Response;

public class GzipFilter
                implements Filter
{
    @Override
    public void handle( Request request, Response response )
    {
        response.header( "Content-Encoding", "gzip" );
    }
}
