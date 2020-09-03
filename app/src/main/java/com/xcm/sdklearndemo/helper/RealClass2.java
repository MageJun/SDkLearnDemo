package com.xcm.sdklearndemo.helper;

import android.util.Log;

/**
 * 描述：
 * 时间： 2020/9/3.
 * 创建： WL
 */

public class RealClass2 implements TestService {
    private static final TestService service = new RealClass2();
    private int count = 1;
    private RealClass2(){

    }
    public static TestService getInstance(){
        return service;
    }
    @Override
    public void ts(String s, int a) {
        Log.i("HookTAG","RealClass2 ts enter ,s = "+s+", a = "+a+",count = "+count);
        count++;
    }

    @Override
    public void t2() {
        Log.i("HookTAG","RealClass2 t2");
    }
}
