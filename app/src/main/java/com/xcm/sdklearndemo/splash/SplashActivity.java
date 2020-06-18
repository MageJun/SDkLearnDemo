package com.xcm.sdklearndemo.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.analytics.sdk.client.AdError;
import com.analytics.sdk.client.AdRequest;
import com.analytics.sdk.client.splash.SplashAdExtListener;
import com.xcm.sdklearndemo.GlobalConfig;
import com.xcm.sdklearndemo.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private AdRequest mAdRequest;
    private boolean canNext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(GlobalConfig.RConfig.SPLASH_ACTIVITY_LAYOUT_ID);

        loadAd();
    }


    private void loadAd() {
        String coid = getIntent().getStringExtra("codid");
        ViewGroup container = findViewById(GlobalConfig.RConfig.SPLASH_ACTIVITY_LAYOUT_AD_ID);
        View skipView = View.inflate(this,GlobalConfig.RConfig.SPLASH_SKIP_LAYOUT_ID,null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 200;
        params.rightMargin = 300;
        mAdRequest = new AdRequest.Builder(this)
                                         .setCodeId(coid)
                                         .setAdContainer(container)
                                         .setSkipContainer(skipView,params)
                                         .build();
        mAdRequest.loadSplashAd(new SplashAdExtListener() {
            @Override
            public void onAdTick(long l) {

            }

            @Override
            public void onAdSkip() {

            }

            @Override
            public void onAdError(AdError adError) {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdShow() {

            }

            @Override
            public void onAdExposure() {

            }

            @Override
            public void onAdDismissed() {
                next();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(canNext){
            next();
        }
        canNext = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        canNext = false;
    }

    private void next(){
        if(canNext) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else {
            canNext = true;
        }
    }
}