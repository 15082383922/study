package com.utstar.utils;

import com.sun.jna.Native;

/**
 * Created by HZ08950 on 2021/8/26/026
 */
public class App {
    public static String getDashboard(String url,String type,String token) {
        String data;
        data = Lib.INSTANCE.get(new GoString.ByValue(url),
                new GoString.ByValue(type),
                new GoString.ByValue(token));
        return data;
    }

    public static void main(String[] args) {
        String get = App.getDashboard("https://10.240.70.215:30000/api/v1/namespace", "GET", "eyJhbGciOiJSUzI1NiIsImtpZCI6IllBOVJnRUx4QTFsekRQWk1pRGdCWWlIckpYMnduWWJtVWZkb1Z5NkFNNVkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLTRqenpwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyYTlkNjQ4My1hODU2LTRkOGQtOWQ4Yy02OThlY2RmOTBiYjIiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.Q5wZ0C-n6N1vwvXClvTCs3ucM5IkYb5kvyk3iegbzwxUbT201bN4-UkWgr-YNW7JkVIn5xnN0S_l7TuBZIyQ5ciP6lXsoPxTvXVQ18lCEm3QS4c_guqpQLOf6GPwm-top5wzsuQO8LAE2mGk58Cb0Q7Tkj8HjGTcP_NBsAowUfigIqpf-RGlWwoosPyIxn9vSEr4mnR0Olfe37Egehkd4IttVilOuigk48hxu_Ze49MmetUlDV5KVJ9Fve2k8-Smukk0Yh6w_tlThZ9ZNKf6t_ci3H3ckvYAg6Y94DECCy-OLB-hDVP2RCUTEbSdtq1dasq01BfjUd8obt8EUQCIDQ");
        System.out.println(get);
    }
}
