package com.xcm.sdklearndemo.helper;

import android.util.Log;

/**
 * 描述：
 * 时间： 2020/9/3.
 * 创建： WL
 */

public class ProxyClass implements TestService {
    private TestService service;

    public ProxyClass(TestService service){
        this.service = service;
    }

    @Override
    public void ts(String s, int a) {
        Log.i("HookTAG","ProxyClass s = "+s+",a = "+a);
        service.ts(s,a);
    }
    @Override
    public void t2() {
        Log.i("HookTAG","ProxyClass RealClass t2");
        service.t2();
    }
}
