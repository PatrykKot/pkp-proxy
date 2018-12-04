package com.kotlarz.api.pkp.train.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil
{
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat( "dd.MM.yyyy HH:mm" );

    public static String toString( Date date )
    {
        return FORMAT.format( date );
    }

    @SneakyThrows
    public static Date parse( String date )
    {
        return FORMAT.parse( date );
    }

    public static Long getTravelTime( String textTravelTime )
    {
        String[] split = textTravelTime.split( ":" );
        Long hours = Long.parseLong( split[0] );
        Long minutes = Long.parseLong( split[1] );

        return TimeUnit.HOURS.toMillis( hours ) + TimeUnit.MINUTES.toMillis( minutes );
    }
}
