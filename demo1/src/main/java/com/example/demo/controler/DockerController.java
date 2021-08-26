package com.example.demo.controler;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@RestController
public class DockerController {
    @GetMapping(value = "hello")
    public Object sayHello() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) {
                return true;
            }
        }).build();
        HttpClient customHttpClient = HttpClients.custom().setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        Unirest.setHttpClient(customHttpClient);

        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://10.240.70.215:30000/api/v1/namespace")
                    .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IllBOVJnRUx4QTFsekRQWk1pRGdCWWlIckpYMnduWWJtVWZkb1Z5NkFNNVkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLTRqenpwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyYTlkNjQ4My1hODU2LTRkOGQtOWQ4Yy02OThlY2RmOTBiYjIiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.Q5wZ0C-n6N1vwvXClvTCs3ucM5IkYb5kvyk3iegbzwxUbT201bN4-UkWgr-YNW7JkVIn5xnN0S_l7TuBZIyQ5ciP6lXsoPxTvXVQ18lCEm3QS4c_guqpQLOf6GPwm-top5wzsuQO8LAE2mGk58Cb0Q7Tkj8HjGTcP_NBsAowUfigIqpf-RGlWwoosPyIxn9vSEr4mnR0Olfe37Egehkd4IttVilOuigk48hxu_Ze49MmetUlDV5KVJ9Fve2k8-Smukk0Yh6w_tlThZ9ZNKf6t_ci3H3ckvYAg6Y94DECCy-OLB-hDVP2RCUTEbSdtq1dasq01BfjUd8obt8EUQCIDQ")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (response != null) {
            System.out.println(response.getBody());
        }
        if (response != null) {
            return response.getBody();
        }
        return "err";
    }
}

