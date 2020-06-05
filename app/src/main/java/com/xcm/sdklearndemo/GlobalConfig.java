package com.xcm.sdklearndemo;

/**
 * 描述：
 * 时间： 2020/6/5.
 * 创建： WL
 *
 * * joker:
 *  * D1960006 dsp 开屏
 *  * joker:
 *  * D1960007 dsp 信息流
 *  * joker:
 *  * D1960008 api 开屏
 *  * joker:
 *  * D1960009 api 信息流
 *  * <p>
 *  * 自有广告 非广点通类型
 *  * <p>
 *  * D2110010	大图1200*800 或 1080*1920  暂时无填充
 *  * <p>
 *  * D2110009	大图800*1200
 *  * <p>
 *  * D2110008	大图1200*627
 *  * <p>
 *  * D2110011 三图  暂时无填充
 */

public final class GlobalConfig {

    /**
     * 在这里添加  广告位 id 可以直接测试
     * 注意记得要 同步修改 gradle#applicationId 改成你们的包名
     * <p>
     * 开发过程中可以用demo中的ID  填充率 100 %
     * 是通用广告位 不验证包名
     */
    public interface ChannelId{
        /**
         * 开屏
         */
        String SPLASH = "D2110001,D1960006,D1960008,D1000112,D1080084,D2110011";
    }

    public interface  RConfig{

        /**
         * 首页
         **/
        int MAIN_ACTIVITY_LAYOUT_ID = R.layout.activity_main;
        int MAIN_ACTIVITY_GRID_ID = R.id.grid_view;

        /**
         * 开屏
         */
        int SPLASH_ACTIVITY_LAYOUT_ID = R.layout.activity_ad_splash;
        int SPLASH_ACTIVITY_LAYOUT_AD_ID = R.id.splash_layout;

    }
}
