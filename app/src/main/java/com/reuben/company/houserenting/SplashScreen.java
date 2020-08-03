package com.reuben.company.houserenting;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashScreen extends AppCompatActivity {
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, SiginAs.class);
                startActivity(intent);
                finish();
            }
        }, 5000);


//        This is for lottie
//        timer = new Thread(){
//            @Override
//            public void run(){
//                try {
//                    synchronized (this){
//                        wait(7000);
//                    }
//
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }finally {
//                    Intent intent = new Intent(SplashScreen.this,MainActivityi.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };
//        timer.start();
    }


}

