package com.br.retrofit_rxjava.ui.activity.splash;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.br.retrofit_rxjava.BR;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.databinding.ActivitySplashBinding;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;
import com.br.retrofit_rxjava.ui.base.BaseActivity;
import com.br.retrofit_rxjava.util.receiver.NetworkBroadcastReceiver;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements
        SplashNavigator,
        NetworkBroadcastReceiver.ConnectivityReceiverListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel().setNavigator(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.viewModel().networkReceiver = null;
        this.viewModel().checkStateNetwork(true);
    }

    @Override
    protected int bindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected SplashViewModel viewModel() {
        return new ViewModelProvider(this).get(SplashViewModel.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Register listener of network state */
        RetrofitRxJavaApplication.of().setNetworkListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.viewModel().unRegisterBroadcast();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) this.connectionReestablished();
    }

    @Override
    public void redirectToHome() {
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

    @Override
    public void onRegisterReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onUnregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void startAnimationInitial() {
        this.databinding.splashAnimation.setVisibility(View.VISIBLE);
        this.databinding.splashAnimation.addAnimatorListener(
                this.viewModel.callbackAnimator(SplashViewModel.TYPE_ANIMATION_INITIAL)
        );
    }

    @Override
    public void startAnimationNoAccessInternet() {
        this.databinding.splashAnimation.setVisibility(View.GONE);
        this.databinding.labelNoInternet.setVisibility(View.VISIBLE);
        this.databinding.llNoInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void connectionReestablished() {
        /* Details */
        this.databinding.labelNoInternet.setVisibility(View.GONE);
        this.databinding.llConnectionReestablished.setVisibility(View.VISIBLE);

        this.databinding.lottieConnectionReestablished.setVisibility(View.VISIBLE);
        this.databinding.llNoInternet.setVisibility(View.GONE);

        this.databinding.lottieConnectionReestablished.addAnimatorListener(
                this.viewModel().callbackAnimator(
                        SplashViewModel.TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS
                )
        );
    }
}