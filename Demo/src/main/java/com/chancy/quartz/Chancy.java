package com.chancy.quartz;

import org.json.JSONObject;

import java.util.*;

public class Chancy {
    private static final Map<Object, Object> objectObjectHashMap = new TreeMap<>();

    static String name = "/mpls:mpls/mpls:explicit-paths/mpls:explicit-path/mpls:nexthop";

    public static String getDaoJson(Map<Object, Object> objectObjectHashMap, String operpath) {
        Set<Object> objects = objectObjectHashMap.keySet();

        List<List<String>> count = new LinkedList<>();
        int max = 0;
        String substring = operpath.substring(operpath.lastIndexOf(":") + 1);
        for (Object object : objects) {
            String s = object.toString();
            String[] split1 = s.replace(operpath, "").substring(1).replace(operpath, "").split("/");
            List<String> list = new ArrayList<>(Arrays.asList(split1));
            count.add(list);
            if (max < list.size()) {
                max = list.size();
            }
        }

        Map<Integer, List<List<String>>> stringListMap = new HashMap<>();
        for (int i = 1; i <= max; i++) {
            List<List<String>> path = new ArrayList<>();
            for (List<String> list : count) {
                if (list.size() == i) {
                    path.add(list);
                }
            }
            stringListMap.put(i, path);
        }
        //分层处理
        Set<Integer> integers = stringListMap.keySet();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectw = new JSONObject();
        JSONObject temp = null;
        for (Integer integer : integers) {

            List<List<String>> lists = stringListMap.get(integer);
            if (integer == 1) {
                for (List<String> list : lists) {
                    String s = list.get(0);
                    jsonObjectw.put(s, getValue(s.substring(s.lastIndexOf(":") + 1), objectObjectHashMap));
                }
                //jsonObject;
            } else if (integer == max) {
                temp = getObject(lists, objectObjectHashMap);

            }

        }
        String s1;
        if (temp != null) {
            s1 = jsonObjectw.toString().substring(0, jsonObjectw.toString().length() - 1) + "," + temp.toString().substring(1);
        } else {
            s1 = jsonObjectw.toString();
        }
        JSONObject jsonObject1 = new JSONObject(s1);
        jsonObject.put(substring, jsonObject1);
        //中间层数据的json对象可以直接生成
        System.out.println();
        return jsonObject.toString().replaceAll("\\[", "{").replaceAll("]", "}").replaceAll(substring + ":", "");
    }

    private static JSONObject getObject(List<List<String>> lists, Map<Object, Object> objectObjectHashMap) {

        List<JSONObject> jsonObjects = new ArrayList<>();
        for (List<String> list : lists) {
            String s = list.get(list.size() - 1);
            JSONObject json = new JSONObject();
            json.put(s, getValue(s.substring(s.lastIndexOf(":") + 1), objectObjectHashMap));
            jsonObjects.add(json);
        }

        List<String> strings1 = lists.get(0);

        return getObject3(strings1, jsonObjects);
    }

    private static JSONObject getObject3(List<String> strings1, List<JSONObject> jsonObjects) {
        JSONObject json = new JSONObject();
        if (strings1.size() == 2) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(strings1.get(0), jsonObjects);
            return jsonObject;
        } else {
            String remove = strings1.remove(0);
            json.put(remove, getObject3(strings1, jsonObjects));
        }
        return json;
    }

    static Object getValue(String s, Map<Object, Object> objectObjectHashMap) {
        Set<Object> objects = objectObjectHashMap.keySet();
        for (Object object : objects) {
            if (object.toString().substring(object.toString().lastIndexOf(":") + 1).equals(s)) {
                return objectObjectHashMap.get(object);
            }
        }
        return null;
    }
}
