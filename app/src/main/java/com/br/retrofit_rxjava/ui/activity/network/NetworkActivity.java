package com.br.retrofit_rxjava.ui.activity.network;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.databinding.ActivityNetworkBinding;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;
import com.br.retrofit_rxjava.ui.activity.splash.SplashViewModel;
import com.br.retrofit_rxjava.ui.base.BaseActivity;

import static com.br.retrofit_rxjava.ui.base.BaseViewModel.TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS;

public class NetworkActivity extends BaseActivity<ActivityNetworkBinding, NetworkViewModel> implements NetworkNavigator {

    @Override
    protected void onStart() {
        super.onStart();
        this.startAnimationNoAccessInternet();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel().setNavigator(this);
    }

    @Override
    protected int bindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected NetworkViewModel viewModel() {
        return new ViewModelProvider(this).get(NetworkViewModel.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_network;
    }

    @Override
    protected boolean verifyChangedNetworkState() {
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            if(isSplash()) {
                this.connectionReestablished(MainActivity.class);
                return;
            }

            this.connectionReestablished(null);
        }
    }

    @Override
    public void startAnimationNoAccessInternet() {
        this.databinding.labelNoInternet.setVisibility(View.VISIBLE);
        this.databinding.llNoInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public <A> void connectionReestablished(Class<A> activity) {
        /* Details */
        this.databinding.labelNoInternet.setVisibility(View.GONE);
        this.databinding.llConnectionReestablished.setVisibility(View.VISIBLE);

        this.databinding.lottieConnectionReestablished.setVisibility(View.VISIBLE);
        this.databinding.llNoInternet.setVisibility(View.GONE);

        this.databinding.lottieConnectionReestablished.addAnimatorListener(
                this.viewModel().callbackAnimator(
                        TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS, activity
                )
        );
    }

    private boolean isSplash() {
        Bundle bundle = getIntent().getExtras();

        return bundle != null && bundle.containsKey(SplashViewModel.SPLASH_SCREEN);
    }
}