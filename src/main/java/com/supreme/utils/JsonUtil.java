package com.supreme.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    public static JSONObject getJSONResource(String fileName) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new InputStreamReader(JsonUtil.class.getClassLoader().getResourceAsStream(fileName)));
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            log.error("Error occurred while attempting to read json file");
            e.printStackTrace();
        }
        return null;
    }
}
