package com.chancy.quartz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {
    static Map<String, String> data = new TreeMap<>();
    static Map<String, String> data2 = new TreeMap<>();

    /*
       <if:interfaces>
     <if:interface>
       <if:name>eth 0/10/1/1.1</if:name>
       <if:type>ETH-SUBIF</if:type>
       <if:pm-statistic-enable>enable</if:pm-statistic-enable>
       <if:is-uni>disable</if:is-uni>
       <l2l3if:transport-layer xmlns:l2l3if="http://chinaunicom.com/yang/interface/chinaunicom-interface-l2l3common">layer-2-switch</l2l3if:transport-layer>
       <vlan:if-vlan-cfg xmlns:vlan="http://chinaunicom.com/yang/ethernetservice/chinaunicom-vlan">
         <vlan:termination_type>dot1q-termination</vlan:termination_type>
       </vlan:if-vlan-cfg>
       <vlan:evc-vlan-cfg xmlns:vlan="http://chinaunicom.com/yang/ethernetservice/chinaunicom-vlan">
         <vlan:evc-encap-cfg>
           <vlan:encap-type>evc-dot1q</vlan:encap-type>
         </vlan:evc-encap-cfg>
         <vlan:evc-rewrite-cfg>
           <vlan:rewrite-type>null</vlan:rewrite-type>
         </vlan:evc-rewrite-cfg>
       </vlan:evc-vlan-cfg>
     </if:interface>
   </if:interfaces>

          {
           "interface":{
               "name":"eth 0/10/1/1.1",
               "type":"ETH-SUBIF",
               "is-uni":"disable",
               "transport-layer":"layer-2-switch",
               "if-vlan-cfg":{
                   "termination_type":"dot1q-termination"
               },
               transport-layer:layer-2-swich,
               "evc-vlan-cfg":{
                   "evc-encap-cfg":{
                       "encap-type":"evc-dot1q"
                   },
                   "evc-rewrite-cfg":{
                       "rewrite-type":"null"
                   }
               }
           }
       }
       */
    static {

        data.put("/vlan:vlan-tag-list/vlan:cvlan_min", "2");
        data.put("/vlan:vlan-tag-list/vlan:cvlan_max", "2");
    }

    static {
        data2.put("/vlan:vlan-tag-list/vlan:cvlan_min", "4");
        data2.put("/vlan:vlan-tag-list/vlan:cvlan_max", "4");
    }
    static Set<String> strings = data2.keySet();

    /*
        data.put("name", "eth 0/10/1/1.1");
        data.put("type", "ETH-SUBIF");//mandatory
        data.put("is-uni", "disable");//mandatory
        data.put("transport-layer", "layer-2-switch");
        data.put("if-vlan-cfg/vlan:termination_type", "dot1q-termination");//mandatory
        data.put("evc-vlan-cfg/vlan:evc-encap-cfg/vlan:encap-type", "evc-dot1q");//mandatory
        data.put("evc-vlan-cfg/vlan:evc-rewrite-cfg/vlan:rewrite-type", "null");//mandatory
     */
    public static void main(String[] args) throws InterruptedException {
        JsonObject jsonObject1 = mapToMap(data);
        System.out.println(jsonObject1.toString());
        JsonObject jsonObject2 = addString(jsonObject1, strings);
        System.out.println(jsonObject2);
    }

    private static JsonObject addString(JsonObject jsonObject1, Set<String> data2) {
        final String o = (String) data2.toArray()[0];
        final String[] split = o.substring(1).split("/");
        for (String s : split) {
            final JsonElement jsonElement = jsonObject1.get(s.substring(s.lastIndexOf(":")));
            if(jsonElement == null) {

            }
        }
        return jsonObject1;
    }

    private static JsonObject mapToMap(Map<String, String> data) {
        Set<String> strings = data.keySet();
        Map<List<String>, String> listStringMap = new LinkedHashMap<>();
        int max = 0;
        for (String string : strings) {
            String regex = "/";
            String[] split = string.replaceFirst("/", "").split(regex);
            if (max < split.length) {
                max = split.length;
            }
            List<String> asList = new ArrayList<>();
            for (String s : split) {
                asList.add(s.substring(s.lastIndexOf(":") + 1));
            }
            listStringMap.put(asList, data.get(string));
        }

        Set<List<String>> lists = listStringMap.keySet();
        ArrayList<List<String>> lists1 = new ArrayList<>(lists);
        JsonObject jsonObject = null;
        for (int i = 0; i < lists1.size(); i++) {
            if (i == 0) {
                jsonObject = getJson(lists1.get(i), listStringMap.get(lists1.get(i)));
            } else {
                getJson(jsonObject, lists1.get(i), listStringMap.get(lists1.get(i)));
            }
        }
        return jsonObject;
    }

    private static void getJson(JsonObject jsonObject, List<String> list, String s) {
        //jsonObject.
        List<String> result = new ArrayList<>();
        JsonObject node = jsonObject;
        for (String value : list) {
            JsonObject jsonElement = node.getAsJsonObject(value);
            if (jsonElement == null) {
                System.out.println(value);
                break;
            } else {
                node = jsonElement;
                result.add(value);
            }
        }
        list.removeAll(result);
        if (list.size() == 1) {
            node.add(list.get(0), new JsonPrimitive(s));
        } else {
            String s1 = list.get(0);
            list.remove(s1);
            JsonObject json = getJson(list, s);
            node.add(s1, json);
        }
    }


    private static JsonObject getJson(List<String> list, String s) {
        JsonObject jsonObject = new JsonObject();
        if (list.size() > 1) {
            String s1 = list.get(0);
            if (list.remove(s1)) {
                jsonObject.add(s1, getJson(list, s));
            }
        } else if (list.size() == 1) {
            jsonObject.addProperty(list.get(0), s);
        }
        return jsonObject;
    }


    @Test
    public void test() {
        JsonObject jsonObject1 = mapToMap2(data);
        System.out.println(jsonObject1);
    }

    private static JsonObject mapToMap2(Map<String, String> data) {
        Set<String> strings = data.keySet();
        Map<List<String>, String> listStringMap = new LinkedHashMap<>();
        int max = 0;
        for (String string : strings) {
            String regex = "/";
            String[] split = string.replaceFirst("/", "").split(regex);
            if (max < split.length) {
                max = split.length;
            }
            List<String> asList = new ArrayList<>();
            for (String s : split) {
                asList.add(s.substring(s.lastIndexOf(":") + 1));
            }
            listStringMap.put(asList, data.get(string));
        }

        Set<List<String>> lists = listStringMap.keySet();
        ArrayList<List<String>> lists1 = new ArrayList<>(lists);
        JsonObject jsonObject = null;
        for (int i = 0; i < lists1.size(); i++) {
            if (i == 0) {
                jsonObject = getJson2(lists1.get(i), listStringMap.get(lists1.get(i)));
            } else {
                getJson2(jsonObject, lists1.get(i), listStringMap.get(lists1.get(i)));
            }
        }
        return jsonObject;
    }

    private static void getJson2(JsonObject jsonObject, List<String> list, String s) {
        //jsonObject.
        List<String> result = new ArrayList<>();
        JsonObject node = jsonObject;
        for (String value : list) {
            JsonObject jsonElement = node.getAsJsonObject(value);

            if (jsonElement == null) {
                System.out.println(value);
                break;
            } else {
                node = jsonElement;
                result.add(value);
            }
        }
        list.removeAll(result);
        if (list.size() == 1) {
            node.add(list.get(0), new JsonPrimitive(s));
        } else {
            String s1 = list.get(0);
            list.remove(s1);
            JsonObject json = getJson2(list, s);
            node.add(s1, json);
        }
    }


    private static JsonObject getJson2(List<String> list, String s) {
        JsonObject jsonObject = new JsonObject();
        if (list.size() > 1) {
            String s1 = list.get(0);
            if (list.remove(s1)) {
                jsonObject.add(s1, getJson2(list, s));
            }
        } else if (list.size() == 1) {

            JsonArray jsonElements = new JsonArray();
            for (String jsonElement : data2.keySet()) {
                if (jsonElement.equals(list.get(0))) {
                    jsonElements.add(data2.get(jsonElement));
                }
            }
            if (jsonElements.size() >= 1) {
                jsonElements.add(s);
                jsonObject.add(list.get(0), jsonElements);
                return jsonObject;
            }
            jsonObject.addProperty(list.get(0), s);
        }
        return jsonObject;
    }




    static Map<String, String>  datas = new TreeMap<>();

    @Test
    public  void show(){
        String operPath = "/dsadsad:65656dasd";
        String s = operPath.replaceFirst("/", "");
        String substring = s.substring(0, s.indexOf(":"));
        System.out.println(substring);
    }
}
