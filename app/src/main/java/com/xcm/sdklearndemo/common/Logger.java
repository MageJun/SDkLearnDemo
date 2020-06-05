package com.xcm.sdklearndemo.common;

import android.util.Log;

/**
 * 描述：
 * 时间： 2020/6/5.
 * 创建： WL
 */

public class Logger {
    static final String MSG_PREFIX = "[SDK_LEARN]";
    public static void i(String tag,String msg){
        Log.i(tag,MSG_PREFIX+"  "+msg);
    }
}
