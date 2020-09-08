package com.br.retrofit_rxjava.ui.base;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.ui.FlowNavigator;
import com.br.retrofit_rxjava.ui.activity.network.NetworkActivity;
import com.br.retrofit_rxjava.ui.dialog.ConfirmDialog;
import com.br.retrofit_rxjava.ui.dialog.listener.ConfirmDialogListener;
import com.br.retrofit_rxjava.util.CommonUtils;
import com.br.retrofit_rxjava.util.receiver.NetworkBroadcastReceiver;

import java.util.Objects;

import static com.br.retrofit_rxjava.ui.activity.network.NetworkViewModel.NETWORK_TAG;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModel<?>> extends AppCompatActivity implements FlowNavigator {

    public V viewModel;

    public B databinding;

    private BroadcastReceiver networkReceiver;

    private Dialog dialog;
    private ConfirmDialog confirmDialog;

    private static final String FILTER_ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.performDataBinding();
        this.confirmDialog = null;
        this.dialog = CommonUtils.loadingDialog(this);
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

    /**
     * Abstract method to get Data Bind variable from Layout Resource.
     */
    protected abstract int bindingVariable();

    /**
     * Abstract method to return 'viewModel' variable.
     */
    protected abstract V viewModel();

    /**
     * Layout resource.
     */
    @LayoutRes
    protected abstract int getLayoutResource();

    /**
     *  Monitor the state of the internet
     */
    protected abstract boolean verifyChangedNetworkState();

    public void showLoading(boolean show) {
        if (show) {
            dialog.show();
        } else {
            if (dialog.isShowing()) dialog.dismiss();
        }
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

    @Override
    public void onAlertConfirm(@StringRes int title, @StringRes int message, @StringRes int[] buttons, boolean isCancelableInTouch,
                               Object payload, ConfirmDialogListener listener) {
        if(buttons.length != 2)
            throw new RuntimeException(getResources().getString(R.string.text_buttons_invalid_size));

        this.confirmDialog = ConfirmDialog.of(this);

        this.confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.confirmDialog.setCancelable(isCancelableInTouch);
        this.confirmDialog.setCanceledOnTouchOutside(isCancelableInTouch);

        this.confirmDialog.viewModel().setData(
                getResources().getString(title),
                getResources().getString(message),
                getResources().getString(buttons[0]),
                getResources().getString(buttons[1]),
                listener,
                payload
        );

        this.confirmDialog.show();

        Objects.requireNonNull(
                this.confirmDialog.getWindow()).setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
        );
    }

    protected void dismissAlertConfirm() {
        if(this.confirmDialog != null && this.confirmDialog.isShowing()){
            this.confirmDialog.dismiss();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected && !getClass().getSimpleName().equals(NETWORK_TAG)) {
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
    protected void onStart() {
        super.onStart();

        /* Checks whether the activity allows you to monitor the state of the internet */
        if(this.verifyChangedNetworkState()) {
            this.onRegisterReceiver();
            return;
        }

        this.networkReceiver = null;
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
        super.onBackPressed();
    }
}