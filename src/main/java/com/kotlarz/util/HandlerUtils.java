package com.kotlarz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Response;

public class HandlerUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void asJson(Response response) {
        response.type("application/json");
    }

    public static String toJson(Object object)
            throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
