package com.br.retrofit_rxjava.ui.activity.splash;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Handler;

import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.ui.base.BaseViewModel;
import com.br.retrofit_rxjava.util.CommonUtils;
import com.br.retrofit_rxjava.util.receiver.NetworkBroadcastReceiver;

public class SplashViewModel extends BaseViewModel<SplashNavigator> {
    public BroadcastReceiver networkReceiver;
    public static final int TYPE_ANIMATION_INITIAL = 1;
    public static final int TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS = 2;

    public void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        this.networkReceiver = new NetworkBroadcastReceiver();
        this.navigator().onRegisterReceiver(this.networkReceiver, filter);
    }

    public void unRegisterBroadcast(){
        if(this.networkReceiver != null)
            this.navigator().onUnregisterReceiver(this.networkReceiver);
    }

    public void checkStateNetwork(Boolean isConnected) {
        if (isConnected) this.navigator().startAnimationInitial();
        else this.navigator().startAnimationNoAccessInternet();
    }

    public Animator.AnimatorListener callbackAnimator(int type){
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                switch (type) {
                    case TYPE_ANIMATION_INITIAL:
                        if(CommonUtils.isNetworkAvailable(RetrofitRxJavaApplication.of().getContext())) {
                            navigator().redirectToHome();
                        }else{
                            new Handler().postDelayed(() -> {
                                checkStateNetwork(false);
                                registerBroadcast();
                            }, 1000L);
                        }
                        break;
                    case TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS:
                        navigator().redirectToHome();
                        break;
                    default: throw new RuntimeException("Invalid entry");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }
}
