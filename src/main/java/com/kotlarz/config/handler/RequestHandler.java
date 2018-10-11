package com.kotlarz.config.handler;

import spark.Service;

public interface RequestHandler
{
    void register( Service service );
}
