/*
 * Copyright (c) 2019 UTStarcom, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package com.chancy.demo;

/**
 * @Author: cq.song
 * @Date: 2019/12/11 11:27
 * @Description
 */
public class Config {
	static int httpConnectTimeout = 3000;
	
	static int httpSocketTimeout = 3000;
	
	static int httpMaxPoolSize = 100;
	
	static int httpMonitorInterval = 3000;
	
	static int httpIdelTimeout = 2000;
	
	public static int getHttpIdelTimeout() {
		return httpIdelTimeout;
	}
	
	public static int getHttpSocketTimeout() {
		return httpSocketTimeout;
	}
	
	public static int getHttpMaxPoolSize() {
		return httpMaxPoolSize;
	}
	
	public static int getHttpMonitorInterval() {
		return httpMonitorInterval;
	}
	
	public static int getHttpConnectTimeout() {
		return httpConnectTimeout;
	}
	
}
