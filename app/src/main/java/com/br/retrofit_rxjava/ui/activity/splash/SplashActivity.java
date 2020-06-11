package com.br.retrofit_rxjava.ui.activity.splash;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;
import com.br.retrofit_rxjava.util.CommonUtils;
import com.br.retrofit_rxjava.util.receiver.NetworkBroadcastReceiver;

public class SplashActivity extends AppCompatActivity implements NetworkBroadcastReceiver.ConnectivityReceiverListener {

    /* GENERAL */
    /* TODO: Optimize this class, i.e, review the use of VM */

    private LottieAnimationView splashAnimation;
    private TextView labelNoInternet;
    private LinearLayout llNoInternet;
    private LinearLayout llConnectionReestablished;
    private LottieAnimationView lottieConnectionReestablished;

    private BroadcastReceiver networkReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.splashAnimation = findViewById(R.id.splash_animation);
        this.labelNoInternet = findViewById(R.id.label_no_internet);
        this.llNoInternet = findViewById(R.id.ll_no_internet);
        this.llConnectionReestablished = findViewById(R.id.ll_connection_reestablished);
        this.lottieConnectionReestablished = findViewById(R.id.lottie_connection_reestablished);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.networkReceiver = null;
        this.checkStateNetwork(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Register listener of network state */
        RetrofitRxJavaApplication.of().setNetworkListener(this);
    }

    private void checkStateNetwork(Boolean isConnected) {
        if (isConnected) {
            delay();
        } else {
            this.splashAnimation.setVisibility(View.GONE);
            this.labelNoInternet.setVisibility(View.VISIBLE);
            this.llNoInternet.setVisibility(View.VISIBLE);
        }
    }

    private void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        this.networkReceiver = new NetworkBroadcastReceiver();
        registerReceiver(this.networkReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.networkReceiver != null)
            unregisterReceiver(this.networkReceiver);
    }

    void delay() {
        this.splashAnimation.setVisibility(View.VISIBLE);
        this.splashAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if(CommonUtils.isNetworkAvailable(SplashActivity.this)) {
                    redirectToHome();
                }else{
                    new Handler().postDelayed(() -> {
                        checkStateNetwork(false);
                        registerBroadcast();
                    }, 1000L);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            this.connectionReestablished();
        }
    }

    private void redirectToHome(){
        new Handler().postDelayed(() -> {
            finish();
            startActivity(
                    new Intent(
                            SplashActivity.this,
                            MainActivity.class
                    )
            );
        }, 1000L);
    }

    private void connectionReestablished(){
        /* Details */
        this.labelNoInternet.setVisibility(View.GONE);
        this.llConnectionReestablished.setVisibility(View.VISIBLE);

        this.lottieConnectionReestablished.setVisibility(View.VISIBLE);
        this.llNoInternet.setVisibility(View.GONE);

        this.lottieConnectionReestablished.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                redirectToHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }
}