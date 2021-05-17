package com.chancy.quartz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HZ08950
 * @date 2021-03-25 15:30
 */
public class LImit {
    private static final Map<String, Map<String, List<String>>> hids = new LinkedHashMap<>();

    public static void main(String[] args) throws FileNotFoundException {

        JsonElement parse = new JsonParser().parse(new InputStreamReader(new FileInputStream("D:\\dev\\study\\Demo\\src\\main\\resources\\config\\yang-field-limit.json")));
        System.out.println(parse.toString());
        if (parse.toString() != null) {
            JsonObject jsonObject = new JsonParser().parse(parse.toString()).getAsJsonObject();
            JsonArray field_refers = jsonObject.getAsJsonArray("limit");
            if (field_refers != null) {
                for (JsonElement jsonElement : field_refers) {
                    String leaf_path = jsonElement.getAsJsonObject().get("leaf_path").toString();
                    JsonElement limit = jsonElement.getAsJsonObject().get("limit");
                    JsonArray asJsonArray = limit.getAsJsonArray();
                    Map<String, List<String>> map = new LinkedHashMap<>();
                    for (JsonElement element : asJsonArray) {
                        String type = element.getAsJsonObject().get("type").toString();
                        JsonElement values = element.getAsJsonObject().get("values");
                        List<String> valueList = new ArrayList<>();
                        for (JsonElement jsonElement1 :values.getAsJsonArray()) {
                            String asString = jsonElement1.getAsString();
                            valueList.add(asString);
                        }
                        map.put(type, valueList);
                    }
                    hids.put(leaf_path, map);
                }
            }
        }
        System.out.println(hids.size());
    }
}
