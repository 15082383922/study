/*
 * Copyright (c) 2019 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.chancy.demo;


import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Author: cq.song
 * @Date: 2019/3/14 15:00
 * @Description
 */
public class HttpClientUtil {
	
	private static final int CONNECT_TIMEOUT = Config.getHttpConnectTimeout();// 设置连接建立的超时时间为10s
	private static final int SOCKET_TIMEOUT = Config.getHttpSocketTimeout();
	private static final int MAX_CONN = Config.getHttpMaxPoolSize(); // 最大连接数
	private static final int Max_PRE_ROUTE = Config.getHttpMaxPoolSize();
	private static final int MAX_ROUTE = Config.getHttpMaxPoolSize();
	private static CloseableHttpClient httpClient; // 发送请求的客户端单例
	private static PoolingHttpClientConnectionManager manager; //连接池管理类
	private static  String USER = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
	private static  String PASSWORD = "";

	public static String getUSER() {
		return USER;
	}

	public static void setUSER(String USER) {
		HttpClientUtil.USER = USER;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String PASSWORD) {
		HttpClientUtil.PASSWORD = PASSWORD;
	}

	private final static Object syncLock = new Object(); // 相当于线程锁,用于线程安全
	
	
	public static String executorDoGet(String url)  {
		try {
			URI uri = URI.create(url);
			HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
			AuthCache authCache = new BasicAuthCache();
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(host, basicAuth);
			CloseableHttpClient httpClient = getHttpClient(uri);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 30).setConnectTimeout(1000 * 30).build();
			HttpGet request = new HttpGet(uri);
			request.setConfig(requestConfig);
			HttpClientContext localContext = HttpClientContext.create();
			localContext.setAuthCache(authCache);
			HttpResponse response = httpClient.execute(host, request, localContext);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
//			throw new PmException("读取网元数据失败", e.getMessage());
			throw new NullPointerException(e.getMessage());
		}

	}

	public static String executorDoGet2(String url,String token)  {
		try {
			URI uri = URI.create(url);
			HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
			SSLContext sslcontext = createIgnoreVerifySSL();
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SocksSSLConnectionSocketFactory(sslcontext)).build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
			CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 30).setConnectTimeout(1000 * 30).build();
			HttpGet request = new HttpGet(uri);
			request.setConfig(requestConfig);
			request.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IllBOVJnRUx4QTFsekRQWk1pRGdCWWlIckpYMnduWWJtVWZkb1Z5NkFNNVkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLTRqenpwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyYTlkNjQ4My1hODU2LTRkOGQtOWQ4Yy02OThlY2RmOTBiYjIiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.Q5wZ0C-n6N1vwvXClvTCs3ucM5IkYb5kvyk3iegbzwxUbT201bN4-UkWgr-YNW7JkVIn5xnN0S_l7TuBZIyQ5ciP6lXsoPxTvXVQ18lCEm3QS4c_guqpQLOf6GPwm-top5wzsuQO8LAE2mGk58Cb0Q7Tkj8HjGTcP_NBsAowUfigIqpf-RGlWwoosPyIxn9vSEr4mnR0Olfe37Egehkd4IttVilOuigk48hxu_Ze49MmetUlDV5KVJ9Fve2k8-Smukk0Yh6w_tlThZ9ZNKf6t_ci3H3ckvYAg6Y94DECCy-OLB-hDVP2RCUTEbSdtq1dasq01BfjUd8obt8EUQCIDQ");
			HttpResponse response = httpClient.execute(host, request);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			throw new NullPointerException(e.getMessage());
		}

	}
	
	public static String doPost(String url, String httpEntity) throws IOException {
		URI uri = URI.create(url);
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(USER, PASSWORD));
		HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
		authCache.put(host, basicAuth);
		// 创建Http Post请求
		HttpPost httpPost = new HttpPost(url);
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAuthCache(authCache);
		httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Connection", "Close");

		// 构建消息实体
		StringEntity entity = new StringEntity(httpEntity, StandardCharsets.UTF_8);
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		// 执行http请求
		HttpResponse response = httpClient.execute(host, httpPost, localContext);
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}

	public static String doPost2(String url, String httpEntity,String token) throws IOException {
		URI uri = URI.create(url);

		HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		// Create AuthCache instance

		// Generate BASIC scheme object and add it to the local auth cache
		CloseableHttpClient httpClient = HttpClients.custom().build();

		// 创建Http Post请求
		HttpPost httpPost = new HttpPost(url);
		HttpClientContext localContext = HttpClientContext.create();
		httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Connection", "Close");
		httpPost.setHeader("Authorization","bearer "+token);
		// 构建消息实体
		StringEntity entity = new StringEntity(httpEntity, StandardCharsets.UTF_8);
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		// 执行http请求
		HttpResponse response = httpClient.execute(host, httpPost, localContext);
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}

	public static CloseableHttpClient getHttpClient(URI uri) {

			//多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁

				if (httpClient == null) {
					httpClient = createHttpClient(uri);
					//开启监控线程,对异常和空闲线程进行关闭
					ScheduledExecutorService monitorExecutor = new ScheduledThreadPoolExecutor(5,
							new BasicThreadFactory.Builder().namingPattern("pm-schedule-pool-%d").daemon(true).build());
					monitorExecutor.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							//关闭异常连接
							manager.closeExpiredConnections();
							//关闭5s空闲的连接
							manager.closeIdleConnections(Config.getHttpIdelTimeout(), TimeUnit.MILLISECONDS);
							//logger.info("close expired and idle for over 5s connection");
						}
					}, Config.getHttpMonitorInterval(), Config.getHttpMonitorInterval(), TimeUnit.MILLISECONDS);
				}


		return httpClient;
	}
	
	/**
	 * 根据host和port构建httpclient实例
	 * @return
	 */
	public static CloseableHttpClient createHttpClient(URI uri) {

		LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
		SSLContext sslcontext = createIgnoreVerifySSL();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SocksSSLConnectionSocketFactory(sslcontext)).build();


		manager = new PoolingHttpClientConnectionManager(registry);
		//设置连接参数
		manager.setMaxTotal(MAX_CONN); // 最大连接数
		manager.setDefaultMaxPerRoute(Max_PRE_ROUTE); // 路由最大连接数
		
		HttpHost httpHost = new HttpHost(uri.getHost(), uri.getPort());
		manager.setMaxPerRoute(new HttpRoute(httpHost), MAX_ROUTE);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(USER, PASSWORD));
		//请求失败时,进行请求重试
		HttpRequestRetryHandler handler = (e, i, httpContext) -> {
			if (i > 3) {
				//重试超过3次,放弃请求
				return false;
			}
			if (e instanceof NoHttpResponseException) {
				//服务器没有响应,可能是服务器断开了连接,应该重试
				return true;
			}
			if (e instanceof SSLHandshakeException) {
				// SSL握手异常
//				logger.error("SSL hand shake exception");
				return false;
			}
			if (e instanceof InterruptedIOException) {
				//超时
				return false;
			}
			if (e instanceof UnknownHostException) {
				// 服务器不可达
				return false;
			}
			if (e instanceof SSLException) {
				return false;
			}
			HttpClientContext context = HttpClientContext.adapt(httpContext);
			HttpRequest request = context.getRequest();
			//如果请求不是关闭连接的请求
			return !(request instanceof HttpEntityEnclosingRequest);
		};
		
		return HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).setDefaultCredentialsProvider(credsProvider).build();
	}
	public static SSLContext createIgnoreVerifySSL() {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("TLSv1.3");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

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

		try {
			if (sc != null) {
				sc.init(null, new TrustManager[] { trustManager }, null);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sc;
	}
	
}
