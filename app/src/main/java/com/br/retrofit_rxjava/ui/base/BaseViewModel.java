package com.br.retrofit_rxjava.ui.base;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;

import androidx.lifecycle.ViewModel;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;
import com.br.retrofit_rxjava.ui.activity.network.NetworkActivity;
import com.br.retrofit_rxjava.ui.activity.splash.SplashViewModel;
import com.br.retrofit_rxjava.util.CommonUtils;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<N extends BaseNavigator> extends ViewModel {

    public static final int TYPE_ANIMATION_SPLASH = 1;
    public static final int TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS = 2;

    private WeakReference<N> navigator = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CompositeDisposable compositeDisposable() {
        return compositeDisposable;
    }

    public N navigator() {
        return this.navigator.get();
    }

    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }

    public void watchDisposable(Disposable disposable) {
        if (disposable != null) this.compositeDisposable.add(disposable);
    }

    public <A> Animator.AnimatorListener callbackAnimator(int type, Class<A> activity) {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                switch (type) {
                    case TYPE_ANIMATION_SPLASH:
                        if (CommonUtils.isNetworkAvailable(RetrofitRxJavaApplication.of().getContext())) {
                            navigator().openActivity(activity, true, null);
                        } else {
                            new Handler().postDelayed(() -> {
                                Bundle bundle = new Bundle();
                                bundle.putString(SplashViewModel.SPLASH_SCREEN, "yes");
                                navigator().openActivity(NetworkActivity.class, true, bundle);
                            }, 1000L);
                        }
                        break;
                    case TYPE_ANIMATION_INTERNET_ACCESS_SUCCESS:
                        if (activity == null) {
                            navigator().onBackPress();
                            return;
                        }

                        navigator().openActivity(activity, true, null);
                        break;
                    default:
                        throw new RuntimeException(
                                RetrofitRxJavaApplication.of().getContext().getResources().getString(
                                        R.string.text_message_invalid_type_animation
                                )
                        );
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        /* Allows you to free resources and threads allocated to Observer through the "dispose" method */
        this.compositeDisposable.dispose();
        /* If you are doing multiple subscriptions, it is very important to clear it to avoid memory leaks */
        //compositeDisposable.clear()
    }
}
