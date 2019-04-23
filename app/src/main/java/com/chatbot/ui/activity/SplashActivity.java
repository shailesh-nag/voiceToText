package com.chatbot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chatbot.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    Handler mHandler = new Handler();
    private startActivity item = new startActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        processTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(item);
    }

    private void processTask() {
        mHandler.postDelayed(item, SPLASH_DISPLAY_LENGTH);
    }

    private class startActivity implements Runnable {

        @Override
        public void run() {
           startActivity(new Intent(SplashActivity.this,MainActivity.class));
           finish();
        }
    }

}
