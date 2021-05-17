package com.chancy.quartz;

import java.util.*;

public class Chancy2 {
    private static final Map<String, String> objectObjectHashMap = new TreeMap<>();
    private static final Map<String, String> data = new TreeMap<>();
    static String name = "/mpls:mpls/mpls:explicit-paths/mpls:explicit-path/mpls:nexthop";

    static {
        objectObjectHashMap.put("/mpls:mpls/mpls:explicit-paths/mpls:explicit-path/mpls:nexthop/mpls:index", "34");
        objectObjectHashMap.put("/mpls:mpls/mpls:explicit-paths/mpls:explicit-path/mpls:nexthop/mpls:constraint", "include");
        objectObjectHashMap.put("/mpls:mpls/mpls:explicit-paths/mpls:explicit-path/mpls:nexthop/mpls:include/mpls:type", "strict");
        objectObjectHashMap.put("/mpls:mpls/mpls:explicit-paths/mpls:explicit-path/mpls:nexthop/mpls:include/mpls:type2", "strict");
    }

    static {
        data.put("mpls:index", "34");
        data.put("mpls:constraint", "include");
        data.put("mpls:type", "strict");
        data.put("mpls:type2", "strict");
    }

    public static void main(String[] args) {
        Set<String> objects = objectObjectHashMap.keySet();
        List<String[]> strings = new ArrayList<>();
        int count = 0;
        for (String object : objects) {
            String replace = object.replace(name + "/", "");
            String[] split = replace.split("/");
            if (count < split.length) {
                count = split.length;
            }
            strings.add(split);
        }
        List<Map<Map<String, String>, String>> maps = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Map<Map<String,String>, String> mapMap = new LinkedHashMap<>();
            Map<String, String> map = new LinkedHashMap<>();
            for (String[] strings1 : strings) {
                if (strings1.length > i) {
                    if (i == strings1.length - 1) {
                        map.put(strings1[i], data.get(strings1[i]));
                    } else {
                        map.put(strings1[i], null);
                    }
                    if (i == 0) {
                        mapMap.put(map, null);
                    } else {
                        mapMap.put(map, strings1[i - 1]);
                    }
                }

            }
            maps.add(mapMap);
        }
        System.out.println(maps);
    }

}
