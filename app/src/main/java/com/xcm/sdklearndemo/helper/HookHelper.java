package com.xcm.sdklearndemo.helper;

import android.content.Context;
import android.util.Log;

import com.qq.e.comm.net.NetworkClient;
import com.qq.e.comm.net.NetworkClientImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import dalvik.system.DexClassLoader;

/**
 * 描述：
 * 时间： 2020/9/3.
 * 创建： WL
 */

public class HookHelper {
    private static final String cls = "com.qq.e.comm.plugin.util.ab";

    public static void hookNetworkMethod(Context context){
        try{
            Log.i("HookTAG","hookNetworkMethod #1");
            DexClassLoader dexClassLoader = HookUtils.getDexClassLoader(context);
            Log.i("HookTAG","hookNetworkMethod #2");
            Class<?> net = dexClassLoader.loadClass("com.qq.e.comm.plugin.t.d");
            Class<?> netin = dexClassLoader.loadClass("com.qq.e.comm.plugin.t.c");
            Log.i("HookTAG","hookNetworkMethod #2-1");
            Method getInstance = net.getMethod("a");
            Log.i("HookTAG","hookNetworkMethod #2-2");
            getInstance.setAccessible(true);
            Object instance = getInstance.invoke(null);
            Log.i("HookTAG","hookNetworkMethod #2-3, instance = "+instance.toString());
//            Log.i("HookTAG","hookNetworkMethod #2");
//            Class<?> net = Class.forName("com.qq.e.comm.plugin.t.d");
            Log.i("HookTAG","hookNetworkMethod #3");
            Field a = net.getDeclaredField("a");
            Log.i("HookTAG","hookNetworkMethod #4 ,field = "+a.toString());
            a.setAccessible(true);
            Log.i("HookTAG","hookNetworkMethod #5");
            Object obj = Proxy.newProxyInstance(netin.getClassLoader(),instance.getClass().getInterfaces(),new NetProxyHandler(instance));
            a.set(instance,obj);
            Log.i("HookTAG","hookNetworkMethod #6");

        }catch (Exception e){
            e.printStackTrace();
            Log.i("HookTAG","hookNetworkMethod #7 exception = "+e.getLocalizedMessage());
        }
    }

    public static void copyFiles(Context context, String fileName, File desFile) {
        Log.i("HookTAG","hookNetworkMethod #copyFiles fileName = "+fileName+",desFile = "+desFile.getAbsolutePath());
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getApplicationContext().getAssets().open(fileName);
            out = new FileOutputStream(desFile.getAbsolutePath());
            byte[] bytes = new byte[1024];
            int i;
            while ((i = in.read(bytes)) != -1)
                out.write(bytes, 0, i);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("HookTAG","hookNetworkMethod copyFiles IOException = "+e.getLocalizedMessage());
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取缓存路径
     *
     * @param context
     * @return 返回缓存文件路径
     */
    public static File getCacheDir(Context context) {
        File localFile1 = context.getApplicationContext().getExternalCacheDir();
        File localFile2 = context.getApplicationContext().getCacheDir();
        if (localFile1 != null){
            return localFile1;
        }
        return localFile2;
    }

    public static DexClassLoader getDexClassLoader(Context context){
        File cacheFile = getCacheDir(context);
        String internalPath = cacheFile.getAbsolutePath() + File.separator + "gdtadv2.jar";
        File desFile = new File(internalPath);
        try {
            desFile.createNewFile();
            copyFiles(context,"gdt_plugin"+File.separator+"gdtadv2.jar", desFile);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("HookTAG","hookNetworkMethod getDexClassLoader  IOException = "+e.getLocalizedMessage());
        }
        String str= cacheFile.getAbsolutePath();
        Log.i("HookTAG","hookNetworkMethod getDexClassLoader cacheFile = "+str);
        DexClassLoader dexClassLoader = new DexClassLoader(internalPath,cacheFile.getAbsolutePath(), null, context.getClass().getClassLoader());
        return dexClassLoader;
    }

    static class NetProxyHandler implements InvocationHandler{
        private Object object;

        public NetProxyHandler(Object obj){
            this.object = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i("HookTAG","I'm hooking!  invoke method = ");
            for (int i = 0;i<args.length;i++){
                Log.i("HookTAG","arg["+i+"]"+" = "+args[i].toString());
            }
            return method.invoke(object,args);
        }
    }


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
//            Object porxyClient = Proxy.newProxyInstance(TestService.class.getClassLoader(),
//                    impl.getClass().getInterfaces(),
//                    new NetworkProxyHandler(impl));
            TestService porxyClient = new ProxyClass(impl);
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

