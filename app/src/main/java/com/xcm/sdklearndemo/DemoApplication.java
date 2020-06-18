package com.xcm.sdklearndemo;

import android.app.Application;

import com.analytics.sdk.client.AdRequest;
import com.analytics.sdk.client.SdkConfiguration;

/**
 * 描述：
 * 时间： 2020/6/18.
 * 创建： WL
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdRequest.init(this,new SdkConfiguration.Builder().build());
    }
}
