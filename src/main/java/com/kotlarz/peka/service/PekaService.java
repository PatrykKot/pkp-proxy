package com.kotlarz.peka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlarz.peka.adapter.dto.StopPoint;
import com.kotlarz.peka.exception.PekaProxyException;
import com.kotlarz.peka.proxy.service.PekaProxyService;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class PekaService
{
    private final String GET_STOP_POINTS_COMMAND = "getStopPoints";

    private PekaProxyService pekaProxyService;

    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    public PekaService( PekaProxyService pekaProxyService )
    {
        this.pekaProxyService = pekaProxyService;
    }

    public List<StopPoint> getStopPointsByName( String name )
    {
        JSONObject response = execute( GET_STOP_POINTS_COMMAND, name );
        return Arrays.asList( asType( response.getJSONArray( "success" ), StopPoint[].class ) );
    }

    @SneakyThrows
    private JSONObject execute( String command, String name )
    {
        String response = pekaProxyService.runCommand( command, name );
        JSONObject jsonResponse = new JSONObject( response );
        if ( jsonResponse.has( "success" ) )
        {
            return jsonResponse;
        }
        else
        {
            throw new PekaProxyException( jsonResponse );
        }
    }

    @SneakyThrows
    private <T> T asType( JSONObject object, Class<T> clazz )
    {
        return mapper.readValue( object.toString(), clazz );
    }

    @SneakyThrows
    private <T> T asType( JSONArray array, Class<T> clazz )
    {
        return mapper.readValue( array.toString(), clazz );
    }
}
