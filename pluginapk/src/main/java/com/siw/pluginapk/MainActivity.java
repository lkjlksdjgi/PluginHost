package com.siw.pluginapk;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    private ImageView watch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watch = (ImageView) findViewById(R.id.watch);
//        watch.setImageResource(R.drawable.watch_anim);
        watch.setImageDrawable(getResources().getDrawable(R.drawable.watch_anim));
        AnimationDrawable animationDrawable = (AnimationDrawable) watch.getDrawable();
        animationDrawable.start();
    }
}
