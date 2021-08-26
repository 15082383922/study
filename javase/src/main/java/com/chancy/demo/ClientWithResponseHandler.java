package com.chancy.demo;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by HZ08950 on 2021/8/23/023
 */
public class ClientWithResponseHandler {
    private static final Logger log = LoggerFactory.getLogger(ClientWithResponseHandler.class);
    public static String doGet(String url) throws Exception {
        SSLContext sslcontext = createIgnoreVerifySSL();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SocksSSLConnectionSocketFactory(sslcontext)).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        // 创建自定义的httpclient对象
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();
        //字符串
        HttpGet get = new HttpGet(url);
        get.setHeader("Authorization", "bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IllBOVJnRUx4QTFsekRQWk1pRGdCWWlIckpYMnduWWJtVWZkb1Z5NkFNNVkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLTRqenpwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyYTlkNjQ4My1hODU2LTRkOGQtOWQ4Yy02OThlY2RmOTBiYjIiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.Q5wZ0C-n6N1vwvXClvTCs3ucM5IkYb5kvyk3iegbzwxUbT201bN4-UkWgr-YNW7JkVIn5xnN0S_l7TuBZIyQ5ciP6lXsoPxTvXVQ18lCEm3QS4c_guqpQLOf6GPwm-top5wzsuQO8LAE2mGk58Cb0Q7Tkj8HjGTcP_NBsAowUfigIqpf-RGlWwoosPyIxn9vSEr4mnR0Olfe37Egehkd4IttVilOuigk48hxu_Ze49MmetUlDV5KVJ9Fve2k8-Smukk0Yh6w_tlThZ9ZNKf6t_ci3H3ckvYAg6Y94DECCy-OLB-hDVP2RCUTEbSdtq1dasq01BfjUd8obt8EUQCIDQ");
        log.info("urlurl===="+url);
        CloseableHttpResponse response = httpclient.execute(get);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity);
        httpclient.close();
        System.out.println(string);

        return string;
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);

        X509TrustManager trustManager = new X509TrustManager() {

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    public static void main(String[] args) throws Exception {
        doGet("https://10.240.70.215:30000/api/v1/namespace");
    }
}
