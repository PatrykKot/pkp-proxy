package com.kotlarz.peka.proxy.service;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PekaProxyServiceTest
{
    private static final String TEST_COMMAND = "getStopPoints";

    private static final String TEST_PATTERN = "";

    private PekaProxyService pekaProxyService;

    @Before
    public void init()
    {
        pekaProxyService = new PekaProxyService();
    }

    @Test
    public void test()
    {
        String content = pekaProxyService.runCommand( TEST_COMMAND, TEST_PATTERN );
        JSONObject json = new JSONObject( content );
        Assert.assertTrue( json.has( "success" ) );
    }
}