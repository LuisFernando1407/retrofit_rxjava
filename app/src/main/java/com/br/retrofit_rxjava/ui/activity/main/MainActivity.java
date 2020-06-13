package com.br.retrofit_rxjava.ui.activity.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.br.retrofit_rxjava.BR;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.databinding.ActivityMainBinding;
import com.br.retrofit_rxjava.ui.base.BaseActivity;
import com.br.retrofit_rxjava.ui.adapter.NoResultAdapter;
import com.br.retrofit_rxjava.ui.dialog.listener.ConfirmDialogListener;
import com.br.retrofit_rxjava.util.CommonUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements
        MainNavigator, ConfirmDialogListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set navigator */
        this.viewModel.setNavigator(this);
    }

    @Override
    protected int bindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected MainViewModel viewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.viewModel.requestCrypto(getResources().getString(R.string.app_crypto_request));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean verifyChangedNetworkState() {
        return true;
    }

    @Override
    public void beforeRequest() {
        this.showLoading(true);
    }

    @Override
    public void afterRequest() {
        this.showLoading(false);
    }

    @Override
    public void onBackPressed() {
        this.onAlertConfirm(
                R.string.text_dialog_title_default,
                R.string.text_dialog_message_exit_default,
                new int[] {
                        R.string.text_dialog_button_cancel_default,
                        R.string.text_dialog_button_yes_default
                },
                false,
                null,
                this
        );
    }

    @Override
    public void showCoins() {
        this.dismissShimmerLayout();

        this.databinding.rvMain.setAdapter(this.viewModel.mainAdapter);
        this.databinding.rvMain.setLayoutManager(CommonUtils.getLinearLayoutMangerDefault(this, true));
    }

    @Override
    public void showErrorOrEmptyList() {
        this.dismissShimmerLayout();

        this.databinding.llSearch.setVisibility(View.GONE);
        this.databinding.rvMain.setAdapter(
                NoResultAdapter.of(
                        this,
                        getResources().getString(R.string.text_no_result_available)
                )
        );
        this.databinding.rvMain.setLayoutManager(CommonUtils.getLinearLayoutMangerDefault(this, false));
    }

    private void dismissShimmerLayout(){
        /* Shimmer layout dismiss */
        this.databinding.shimmerLayout.setVisibility(View.GONE);
        this.databinding.shimmerLayout.stopShimmerAnimation();
    }

    @Override
    public void onConfirm(Object payload) {
        this.finishAndRemoveTask();
    }
}