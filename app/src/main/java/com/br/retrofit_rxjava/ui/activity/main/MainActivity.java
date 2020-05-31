package com.br.retrofit_rxjava.ui.activity.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.br.retrofit_rxjava.BR;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.databinding.ActivityMainBinding;
import com.br.retrofit_rxjava.ui.base.BaseActivity;
import com.br.retrofit_rxjava.ui.adapter.NoResultAdapter;
import com.br.retrofit_rxjava.util.CommonUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {

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
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.viewModel.requestCrypto("btc");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
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
    public void onBackPressed() {}

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
        this.databinding.rvMain.setAdapter(NoResultAdapter.of(this, "NO RESULT AVAILABLE"));
        this.databinding.rvMain.setLayoutManager(CommonUtils.getLinearLayoutMangerDefault(this, false));
    }

    private void dismissShimmerLayout(){
        /* Example shimmer layout - dismiss */
        this.databinding.shimmerLayout.setVisibility(View.GONE);
        this.databinding.shimmerLayout.stopShimmerAnimation();
    }
}