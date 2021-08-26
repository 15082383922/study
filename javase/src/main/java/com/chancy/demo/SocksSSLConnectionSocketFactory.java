package com.chancy.demo;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.SSLContext;

/**
 * Created by HZ08950 on 2021/8/23/023
 */
public class SocksSSLConnectionSocketFactory extends SSLConnectionSocketFactory {
    public SocksSSLConnectionSocketFactory(SSLContext sslContext) {
        super(sslContext, new DisableHostnameVerifier());
    }
}
