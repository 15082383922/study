package com.chancy.quartz;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ZCM {
    private static String getHostIp() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            Enumeration<InetAddress> nias = ni.getInetAddresses();
            while (nias.hasMoreElements()) {
                InetAddress ia = nias.nextElement();
                if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                    String ip = ia.toString();
                    return ip.substring(1, ip.length());
                }
            }
        }
        return null;
    }

    /*ublic static void main(String[] args) throws SocketException {
        System.out.println(getHostIp());
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("a","aa");
        stringMap.put("b","a");
        stringMap.put("c","a");
        final Object[] values = stringMap.values().toArray();
        List<String> a = new ArrayList<>(stringMap.values());
        System.out.println(a.get(0));
    }*/


    public static void main(String[] args) {
        String range = "1..3|4..6|8..25|29|65|51..57";
        String string = "2..5|21|6|15..19|25|29|54..69";
        String s = rangeCovert(range,string);
        System.out.println(s);
    }

    private static String rangeCovert(String range, String string) {
        final String[] split = range.split("\\|");
        List<String> strings = new ArrayList<>();
        List<String> result2 = new ArrayList<>();
        List<Integer> list = stringToInt(string.split("\\|"), strings);
        for (String s : split) {
            if (s.contains("..")) {
                final String[] split1 = s.split("\\.\\.");
                final int r0 = Integer.parseInt(split1[0]);
                final int r1 = Integer.parseInt(split1[1]);

                for (int i = r0; i < r1 + 1; i++) {
                    if (list.contains(i)) {
                        result2.add(i + "");
                    }
                }
                for (String value : strings) {
                    final String[] split2 = value.split("\\.\\.");
                    final int s0 = Integer.parseInt(split2[0]);
                    final int s1 = Integer.parseInt(split2[1]);
                    if (s0 > r0 && s1 < r1) {
                        result2.add(value);
                    } else if (s0 < r0 && s1 > r1) {
                        result2.add(s);
                    } else if (s0 > r0 && s1 > r1 && s0 < r1) {
                        result2.add(s0 + ".." + r1);
                    } else if (s0 < r0 && s1 < r1 && s1 > r0) {
                        result2.add(r0 + ".." + s1);
                    }
                }
            } else {
                final int nub = Integer.parseInt(s);
                if (list.contains(nub)) {
                    result2.add(nub + "");
                }
                for (String value : strings) {
                    final String[] split2 = value.split("\\.\\.");
                    final int s0 = Integer.parseInt(split2[0]);
                    final int s1 = Integer.parseInt(split2[1]);
                    for (int i = s0; i < s1 + 1; i++) {
                        if (i == nub) {
                            result2.add(nub + "");
                        }
                    }
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < result2.size(); i++) {
            stringBuilder.append(result2.get(i));
            if (i != result2.size() - 1) {
                stringBuilder.append("|");
            }
        }
        return stringBuilder.toString();
    }

    private static List<Integer> stringToInt(String[] split, List<String> result) {
        List<Integer> integers = new ArrayList<>();
        for (String s : split) {
            if (!s.contains("..")) {
                integers.add(Integer.parseInt(s));
            } else {
                result.add(s);
            }
        }
        return integers;
    }
}
