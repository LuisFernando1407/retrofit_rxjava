package com.br.retrofit_rxjava.ui.activity.splash;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.br.retrofit_rxjava.BR;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.databinding.ActivitySplashBinding;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;
import com.br.retrofit_rxjava.ui.base.BaseActivity;

import static com.br.retrofit_rxjava.ui.base.BaseViewModel.TYPE_ANIMATION_SPLASH;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements
        SplashNavigator {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel().setNavigator(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.startAnimationInitial();
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
    public void startAnimationInitial() {
        this.databinding.splashAnimation.setVisibility(View.VISIBLE);
        this.databinding.splashAnimation.addAnimatorListener(
                this.viewModel().callbackAnimator(TYPE_ANIMATION_SPLASH, MainActivity.class)
        );
    }

    @Override
    protected boolean verifyChangedNetworkState() {
        return false;
    }
}