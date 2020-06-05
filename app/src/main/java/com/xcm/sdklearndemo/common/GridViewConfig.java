package com.xcm.sdklearndemo.common;

import android.view.View;

/**
 * 描述：
 * 时间： 2020/6/5.
 * 创建： WL
 */

public class GridViewConfig {

    public String text;
    public String codeid;
    public Class activity;
    public View.OnClickListener clickListener;

    public GridViewConfig(String text, String codeid, Class activity) {
        this.text = text;
        this.codeid = codeid;
        this.activity = activity;
    }

    public GridViewConfig(String text, String codeid, Class activity, View.OnClickListener clickListener) {
        this.text = text;
        this.codeid = codeid;
        this.activity = activity;
        this.clickListener = clickListener;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
