package com.xcm.sdklearndemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xcm.sdklearndemo.common.GridViewConfig;
import com.xcm.sdklearndemo.helper.HookHelper;
import com.xcm.sdklearndemo.helper.RealClass2;
import com.xcm.sdklearndemo.helper.TestService;
import com.xcm.sdklearndemo.splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private int count = 0;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            TestService service = RealClass2.getInstance();
            Log.i("HookTAG","service = "+service);
            service.ts("hh",count);
            service.t2();
            count++;
            mHandler.sendEmptyMessageDelayed(0,5*1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(GlobalConfig.RConfig.MAIN_ACTIVITY_LAYOUT_ID);

        //commit test1

        //commit test2

        //delete commit test3

        //commit test4

        //commit test5

        initView();
        TestService service = RealClass2.getInstance();
        Log.i("HookTAG","service = "+service);
        service.ts("hh",count);
        count++;
        HookHelper.hookTestService();
        mHandler.sendEmptyMessage(0);

    }

    private void initView() {
//        HookHelper.hookNetwork(getApplicationContext());
        gridView = findViewById(GlobalConfig.RConfig.MAIN_ACTIVITY_GRID_ID);
        List<GridViewConfig> data = new ArrayList<GridViewConfig>();
        String testStr = "Hello worold";
        Log.i("TestTag",testStr);
        data.add(new GridViewConfig("开屏",GlobalConfig.ChannelId.SPLASH, SplashActivity.class));
        data.add(new GridViewConfig("测试", "", SplashActivity.class, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this,TestSingleTaskActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
            }
        }));
        GridAdapter adapter = new GridAdapter();
        adapter.addData(data);
        gridView.setAdapter(adapter);

    }

    class GridAdapter extends BaseAdapter{
        private List<GridViewConfig> data = new ArrayList<GridViewConfig>();
        private TextView title;
        private TextView codid;

        public void addData(List<GridViewConfig> list){
            data.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gridview_item, null);
            title = inflate.findViewById(R.id.gridView_item_text);
            codid = inflate.findViewById(R.id.gridView_item_codid);
            title.setText(data.get(position).getText());
            String adId = data.get(position).getCodeid().equals("") ? "无" : data.get(position).getCodeid();
            final String[] adIdArray = adId.split(",");
            codid.setText(adIdArray[0]);
            if (data.get(position).clickListener != null) {
                inflate.setOnClickListener(data.get(position).clickListener);
            } else {
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adIdArray.length > 1) {
                            LinearLayout inflate1 = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_popwin, null);
                            final PopupWindow popupWindow = new PopupWindow(parent.getContext());
                            popupWindow.setFocusable(true);
                            popupWindow.setContentView(inflate1);
                            for (final String s1 : adIdArray) {
                                Button button = new Button(parent.getContext());
                                button.setText(s1);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(parent.getContext(), data.get(position).getActivity());
                                        intent.putExtra("codid", s1);
                                        parent.getContext().startActivity(intent);
                                        popupWindow.dismiss();
                                    }
                                });
                                inflate1.addView(button);
                            }
                            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        } else {
                            Intent intent = new Intent(parent.getContext(), data.get(position).getActivity());
                            intent.putExtra("codid", adIdArray[0]);
                            parent.getContext().startActivity(intent);
                        }
                    }
                });
            }

            return inflate;
        }
    }
}