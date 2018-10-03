package com.kotlarz.service;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PekaServiceTest
{
    private static final String TEST_COMMAND = "getStopPoints";

    private static final String TEST_PATTERN = "";

    private PekaService pekaService;

    @Before
    public void init()
    {
        pekaService = new PekaService();
    }

    @Test
    public void test()
                    throws IOException
    {
        String content = pekaService.runCommand( TEST_COMMAND, TEST_PATTERN );
        JSONObject json = new JSONObject( content );
        Assert.assertTrue( json.has( "success" ) );
    }
}