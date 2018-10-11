package com.kotlarz.peka.adapter.exception;

import lombok.AllArgsConstructor;
import org.json.JSONObject;

@AllArgsConstructor
public class PekaProxyException
                extends RuntimeException
{
    private JSONObject json;

    @Override
    public String getMessage()
    {
        return json.getString( "failure" );
    }
}
