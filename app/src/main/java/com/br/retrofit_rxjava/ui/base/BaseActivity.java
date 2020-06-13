package com.br.retrofit_rxjava.ui.base;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.ui.FlowNavigator;
import com.br.retrofit_rxjava.ui.activity.main.MainActivity;
import com.br.retrofit_rxjava.ui.activity.network.NetworkActivity;
import com.br.retrofit_rxjava.ui.activity.network.NetworkNavigator;
import com.br.retrofit_rxjava.util.CommonUtils;
import com.br.retrofit_rxjava.util.receiver.NetworkBroadcastReceiver;

import timber.log.Timber;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModel<?>> extends AppCompatActivity implements FlowNavigator {

    private Dialog dialog;
    public V viewModel;

    public B databinding;

    private BroadcastReceiver networkReceiver;

    private static final String FILTER_ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.performDataBinding();
        this.dialog = CommonUtils.loadingDialog(this);
    }

    /**
     * Abstract method to get Data Bind variable from Layout Resource.
     */
    protected abstract int bindingVariable();

    /**
     * Abstract method to return 'viewModel' variable.
     */
    protected abstract V viewModel();

    /**
     * Layout resource companyId.
     */
    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract boolean verifyChangedNetworkState();

    public void showLoading(boolean show) {
        if (show) {
            dialog.show();
        } else {
            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    /**
     * Configure data bind with lifecycle of this Activity.
     */
    public void performDataBinding() {
        this.databinding = DataBindingUtil.setContentView(this, getLayoutResource());
        this.viewModel = this.viewModel();

        this.databinding.setVariable(this.bindingVariable(), this.viewModel);
        this.databinding.executePendingBindings();
    }

    @Override
    public void onAlertError(String title, String message) {
        /* Set your alert error */
        CommonUtils.alert(this, title, message);
    }

    @Override
    public void onAlertSuccess(String title, String message) {
        /* Set your alert success */
        CommonUtils.alert(this, title, message);
    }

    public void onRegisterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(FILTER_ACTION_CONNECTIVITY_CHANGE);

        this.networkReceiver = new NetworkBroadcastReceiver();
        this.registerReceiver(this.networkReceiver, filter);
    }

    public void onUnregisterReceiver() {
        if (this.networkReceiver != null) {
            this.unregisterReceiver(this.networkReceiver);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected && !getClass().getSimpleName().equals("NetworkActivity")) {
            this.openActivity(NetworkActivity.class, false, null);
        }
    }

    @Override
    public <A> void openActivity(Class<A> activity, boolean isFinished, Bundle bundle) {
        if (isFinished) finish();
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (this.verifyChangedNetworkState()) {
            this.onRegisterReceiver();
        } else {
            this.networkReceiver = null;
        }
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

        this.onUnregisterReceiver();
    }

    @Override
    public void onBackPress() {
        this.onBackPressed();
    }
}