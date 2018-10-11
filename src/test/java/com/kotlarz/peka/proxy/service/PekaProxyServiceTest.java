package com.kotlarz.peka.proxy.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PekaProxyServiceTest {
    private static final String TEST_COMMAND = "getStopPoints";

    private static final String TEST_ARGUMENT_NAME = "pattern";

    private static final String TEST_ARGUMENT = "bałt";

    private PekaProxyService pekaProxyService;

    @Before
    public void init() {
        pekaProxyService = new PekaProxyService();
    }

    @Test
    public void runCommandTest()
            throws IOException {
        String content = pekaProxyService.runCommand(TEST_COMMAND, TEST_ARGUMENT_NAME, TEST_ARGUMENT);
        JSONObject json = new JSONObject(content);
        Assert.assertTrue(json.has("success"));
        JSONArray successArray = json.getJSONArray("success");

        Boolean found = false;
        for (int i = 0; i < successArray.length(); i++) {
            JSONObject item = successArray.getJSONObject(i);
            if (item.getString("name").toLowerCase().contains(TEST_ARGUMENT)) {
                found = true;
            }
        }

        Assert.assertTrue(found);
    }
}