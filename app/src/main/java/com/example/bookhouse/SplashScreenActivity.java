package com.example.bookhouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    Animation topanimation, bottomanimation;
    ImageView imageView;
    TextView textView, fri;
    private static int t = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        topanimation = AnimationUtils.loadAnimation(this, R.anim.topanim);
        bottomanimation = AnimationUtils.loadAnimation(this, R.anim.bottomanim);
        imageView = findViewById(R.id.splashimg);
        textView = findViewById(R.id.splashtext);
        imageView.setAnimation(topanimation);
        textView.setAnimation(bottomanimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, t);
    }
}