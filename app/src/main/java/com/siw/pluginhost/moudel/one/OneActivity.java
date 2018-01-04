package com.siw.pluginhost.moudel.one;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.siw.pluginhost.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class OneActivity extends AppCompatActivity {

    private ImageView watch;
    private String fileName;
    private String packagename;
    private File apkFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        watch = (ImageView) findViewById(R.id.watch);
        downLoadApk();
    }

    private void downLoadApk() {
        fileName = "plugin.apk";
        String filePath = this.getCacheDir() + File.separator + fileName;
        //插件apk的包名
        packagename = "com.siw.pluginapk";
        apkFile = new File(filePath);
        try {
            InputStream inputStream = this.getAssets().open(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *加载插件apk的资源文件，主要就是获取插件apk的Resource，但怎么去获取插件apk的Resource呢？
     * 通过android源码可以看到，当一个app启动的时候，会实例化一个Resource，
     * 然后我们可以在Activity或Fragment等等里面通过context.getResource来获取资源资源文件，这个Resource加载的是dada/data/package/宿主apk的资源，
     * 而他加载dada/data/package/宿主apk的资源就是通过AssetsManager加载的。
     * 所以要想获取插件apk的Resource可以通过AssetsManager和插件在sd的路径即可
     *
     */
    public void click(View view) {
        AssetManager pluginApkAssetManager = getPluginApkAssetManager(apkFile);
        Resources pluginResources = new Resources(pluginApkAssetManager, getResources().getDisplayMetrics(), getResources().getConfiguration());
        try {
            Drawable pluginDrawable = getPluginDrawable(pluginResources);
            watch.setImageDrawable(pluginDrawable);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Drawable getPluginDrawable(Resources pluginResources) throws ClassNotFoundException, IllegalAccessException {
        //反射插件apk的R文件
        DexClassLoader dexClassLoader = new DexClassLoader(apkFile.getAbsolutePath(),this.getDir(fileName, Context.MODE_PRIVATE).getAbsolutePath(),null,this.getClassLoader());
        //因为动画资源放在插件的drawable目录下，所以是drawable，具体的看在哪个资源文件夹下，如在anim下就写anim
        Class<?> loadClass = dexClassLoader.loadClass(packagename + ".R$drawable");
        Field[] declaredFields = loadClass.getDeclaredFields();
        for (Field field:declaredFields){
            //watch_anim是资源文件的名字
            if(field.getName().equals("watch_anim")){
                int animId = field.getInt(R.anim.class);
                Drawable drawable = pluginResources.getDrawable(animId);
                return drawable;
            }
        }
        return null;
    }

    //获取插件apk的AssetManager
    public AssetManager getPluginApkAssetManager(File apkFile){
        try {
            Class<?> forName = Class.forName("android.content.res.AssetManager");
            Method[] declaredMethods = forName.getDeclaredMethods();
            for(Method method:declaredMethods){
                if(method.getName().equals("addAssetPath")){
                    AssetManager assetManager = AssetManager.class.newInstance();
                    method.invoke(assetManager,apkFile.getAbsolutePath());
                    return assetManager;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
