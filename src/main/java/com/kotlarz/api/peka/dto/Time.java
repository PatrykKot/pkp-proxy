package com.kotlarz.api.peka.dto;

import com.kotlarz.api.peka.times.adapter.dto.PekaTime;
import com.kotlarz.api.peka.times.adapter.dto.PekaTimeDetails;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Time
{
    private Boolean gpsTime;

    private Long minutes;

    private String direction;

    private Boolean onStop;

    private Date departure;

    private String line;

    public static List<Time> from( PekaTime time )
    {
        List<PekaTimeDetails> times = time.getTimes();
        return times.stream()
                        .map( Time::from )
                        .collect( Collectors.toList() );
    }

    public static Time from( PekaTimeDetails timeDetails )
    {
        return Time.builder()
                        .gpsTime( timeDetails.getRealTime() )
                        .minutes( timeDetails.getMinutes() )
                        .direction( timeDetails.getDirection() )
                        .onStop( timeDetails.getOnStopPoint() )
                        .departure( fromDeparture( timeDetails.getDeparture() ) )
                        .line( timeDetails.getLine() )
                        .build();
    }

    @SneakyThrows
    private static Date fromDeparture( String date )
    {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );
        return format.parse( date.replace( "T", " " ).replace( "Z", "" ) );
    }
}
