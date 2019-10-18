package com.project.android_kidstories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.os.Bundle;
import android.os.Handler;

import com.project.android_kidstories.Views.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        ImageView bounceImage = findViewById(R.id.logo);
        bounceImage.startAnimation(bounce);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);
                finish();
            }
            //the delay time is 3s
        }, 3000);

    }
}
