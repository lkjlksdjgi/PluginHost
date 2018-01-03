package com.siw.pluginhost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.siw.pluginhost.moudel.four.FourActivity;
import com.siw.pluginhost.moudel.one.OneActivity;
import com.siw.pluginhost.moudel.three.ThreeActivity;
import com.siw.pluginhost.moudel.two.TwoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button one;
    private Button two;
    private Button three;
    private Button four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one://加载apk插件资源文件
                startActivity(new Intent(this, OneActivity.class));
                break;
            case R.id.two://加载apk插件java代码
                startActivity(new Intent(this, TwoActivity.class));
                break;
            case R.id.three://加载apk插件Activity
                startActivity(new Intent(this, ThreeActivity.class));
                break;
            case R.id.four://加载apk插件Fragment
                startActivity(new Intent(this, FourActivity.class));
                break;
        }
    }
}
