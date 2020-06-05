package com.xcm.sdklearndemo.splash;

import android.os.Bundle;

import com.xcm.sdklearndemo.GlobalConfig;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(GlobalConfig.RConfig.SPLASH_ACTIVITY_LAYOUT_ID);
    }
}