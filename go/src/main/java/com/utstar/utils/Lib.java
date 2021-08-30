package com.utstar.utils;

/**
 * Created by HZ08950 on 2021/8/26/026
 */

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public interface Lib extends Library {
    Lib INSTANCE = Native.loadLibrary((Platform.isWindows() ? "callgo" : "callgo"), Lib.class);
    String get(GoString.ByValue url,GoString.ByValue type,GoString.ByValue pwd);
}
