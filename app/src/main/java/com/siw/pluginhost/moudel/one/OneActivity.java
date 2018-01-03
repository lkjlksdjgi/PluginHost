package com.siw.pluginhost.moudel.one;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.siw.pluginhost.R;

public class OneActivity extends AppCompatActivity {

    private ImageView watch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        watch = (ImageView) findViewById(R.id.watch);
        /**
         * 一般来说，加载资源文件都是通过 getResources().getDrawable()来获取资源，而这个getResources()是在Context实例化
         * 之后产生的，而Context是在Apk启动之后被实例化的，所以
         *
         *
         */
        

    }
}
