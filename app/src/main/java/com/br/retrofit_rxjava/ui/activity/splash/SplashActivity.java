package com.br.retrofit_rxjava.ui.activity.splash;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView splashAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.splashAnimation = findViewById(R.id.splash_animation);
        this.delay();
    }

    void delay(){
        this.splashAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                startActivity(
                        new Intent(
                                SplashActivity.this,
                                MainActivity.class
                        )
                );
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }
}
