package com.kotlarz;

import spark.utils.StringUtils;

public class AppArguments
{
    public static String KEYSTORE_PATH;

    public static String KEYSTORE_PASSWORD;

    public static Integer PORT = 8080;

    public static void initPort( String port )
    {
        if ( !StringUtils.isBlank( port ) )
        {
            PORT = Integer.parseInt( port );
        }
    }
}
