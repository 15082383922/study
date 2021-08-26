package com.chancy.demo;

import com.bazaarvoice.jolt.JsonUtils;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by HZ08950 on 2021/8/23/023
 */
public class TokenTest {
    public static void main(String[] args) throws Exception {
        HttpURLConnection connection = connectToWeb("https://10.240.70.215:30000/api/v1/namespace", "GET");

        int result = connection.getResponseCode();
        if (result == 200) {
            Object object = getResult(connection.getInputStream());
            System.out.println(JsonUtils.toJsonString(object));
        } else {
            System.out.println(".........fail..........");
        }
        connection.disconnect();
    }

    public static String getResult(InputStream inputStream) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    bo.write(data, 0, len);
                }
                result = new String(bo.toByteArray(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static HttpURLConnection connectToWeb(String uri, String method) {
        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((arg0, arg1) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IllBOVJnRUx4QTFsekRQWk1pRGdCWWlIckpYMnduWWJtVWZkb1Z5NkFNNVkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLTRqenpwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyYTlkNjQ4My1hODU2LTRkOGQtOWQ4Yy02OThlY2RmOTBiYjIiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.Q5wZ0C-n6N1vwvXClvTCs3ucM5IkYb5kvyk3iegbzwxUbT201bN4-UkWgr-YNW7JkVIn5xnN0S_l7TuBZIyQ5ciP6lXsoPxTvXVQ18lCEm3QS4c_guqpQLOf6GPwm-top5wzsuQO8LAE2mGk58Cb0Q7Tkj8HjGTcP_NBsAowUfigIqpf-RGlWwoosPyIxn9vSEr4mnR0Olfe37Egehkd4IttVilOuigk48hxu_Ze49MmetUlDV5KVJ9Fve2k8-Smukk0Yh6w_tlThZ9ZNKf6t_ci3H3ckvYAg6Y94DECCy-OLB-hDVP2RCUTEbSdtq1dasq01BfjUd8obt8EUQCIDQ");
            connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
