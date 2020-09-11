package com.xcm.sdklearndemo.helper;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * 描述：
 * 时间： 2020/9/9.
 * 创建： WL
 */

class HookUtils {
    private static final String TAG = "HookUtils";
    private static final String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String pubPath = "e_qq_com_plugin";

    public static DexClassLoader getDexClassLoader(Context context){
        Log.i(TAG,"getDexClassLoader enter");
        File dir = context.getDir(buildNevPathByProcessName(context),0);
        File file = new File(dir,"gdt_plugin.jar");
        Log.i(TAG,"getDexClassLoader file = "+file.getAbsolutePath()+", dir = "+dir.getAbsolutePath());
        return new DexClassLoader(file.getAbsolutePath(),dir.getAbsolutePath(),null,context.getClass().getClassLoader());
    }

    public static String buildNevPathByProcessName(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append(pubPath);
        sb.append("_");
        String processName = getProcessName(context);
        Log.i(TAG, "buildNevPathByProcessName processName = " + processName);
        String encode_p = encode(processName);

        sb.append(encode_p);
        Log.i(TAG, "buildNevPathByProcessName result = " + sb.toString());
        return sb.toString();
    }

    public static String getProcessName(Context cxt) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static String encode(String origin)
    {
        String str = null;
        try
        {
            str = new String(origin);
            str = byteArrayToHexString(( MessageDigest.getInstance("MD5")).digest(str.getBytes("UTF-8")));
        }
        catch (Exception localException)
        {
        }
        return str;
    }
    public static String byteArrayToHexString(byte[] b)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < b.length; i++)
        {
            int j;
            if ((j = j = b[i]) < 0)
                j += 256;
            int k = j / 16;
            j %= 16;
            localStringBuffer.append(a[k] + a[j]);
        }
        return localStringBuffer.toString();
    }
}
