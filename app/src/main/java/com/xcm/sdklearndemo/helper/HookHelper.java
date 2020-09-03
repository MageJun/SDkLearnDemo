package com.xcm.sdklearndemo.helper;

import android.content.Context;
import android.util.Log;

import com.qq.e.comm.net.NetworkClient;
import com.qq.e.comm.net.NetworkClientImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 描述：
 * 时间： 2020/9/3.
 * 创建： WL
 */

public class HookHelper {


    public static void hookNetwork(Context context){
        Log.i("HookTAG","hookNetwork #1");
        NetworkClientImpl impl = (NetworkClientImpl) NetworkClientImpl.getInstance();

        try {
            Log.i("HookTAG","hookNetwork #2");
            Field a = NetworkClientImpl.class.getDeclaredField("a");
            Log.i("HookTAG","hookNetwork #3");
            a.setAccessible(true);
            Log.i("HookTAG","hookNetwork #4");
            Object porxyClient = Proxy.newProxyInstance(NetworkClient.class.getClassLoader(),
                    impl.getClass().getInterfaces(),
                    new NetworkProxyHandler(impl));
            Log.i("HookTAG","hookNetwork #5");
            a.set(impl,porxyClient);
            Log.i("HookTAG","hookNetwork #6");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void hookTestService(){
        Log.i("HookTAG","hookTestService #1");
        TestService impl =  RealClass2.getInstance();

        try {
            Log.i("HookTAG","hookTestService #2");
            Field service = RealClass2.class.getDeclaredField("service");
            Log.i("HookTAG","hookTestService #3");
            service.setAccessible(true);
            Log.i("HookTAG","hookTestService #4");
            Object porxyClient = Proxy.newProxyInstance(TestService.class.getClassLoader(),
                    impl.getClass().getInterfaces(),
                    new NetworkProxyHandler(impl));
            Log.i("HookTAG","hookTestService #5");
            service.set(impl,porxyClient);
            Log.i("HookTAG","hookTestService #6");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

class NetworkProxyHandler implements InvocationHandler{
    private Object object;

    public NetworkProxyHandler(Object o){
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("HookTAG","I'm hooking! ,method = "+method.getName());
        return method.invoke(object,args);
    }
}

