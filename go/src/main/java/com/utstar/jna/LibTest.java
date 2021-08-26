package com.utstar.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by HZ08950 on 2021/8/26/026
 */
public interface LibTest extends Library {
    LibTest INSTANCE = Native.loadLibrary("\\dll\\callgo", LibTest.class);

    String get(GoString.ByValue url,GoString.ByValue type,GoString.ByValue pwd);
    int sum(int a, int b);
    String show(GoString.ByValue a, GoString.ByValue b);
}
