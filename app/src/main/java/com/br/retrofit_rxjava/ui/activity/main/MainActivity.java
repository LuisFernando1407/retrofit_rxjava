package com.br.retrofit_rxjava.ui.activity.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.model.Crypto;
import com.br.retrofit_rxjava.ui.activity.base.BaseActivity;
import com.br.retrofit_rxjava.ui.adapter.MainAdapter;
import com.br.retrofit_rxjava.ui.adapter.NoResultAdapter;
import com.br.retrofit_rxjava.util.Util;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainViewModel> implements MainNavigator {

    /* TODO: Set Data Binding */
    @BindView(R.id.rvMain)
    public RecyclerView rvMain;

    @BindView(R.id.edtSearch)
    public AppCompatEditText edtSearch;

    @BindView(R.id.llSearch)
    public LinearLayout llSearch;

    @BindView(R.id.shimmerLayout)
    public ShimmerFrameLayout shimmerLayout;

    private MainAdapter mainAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listeners();
    }

    @Override
    protected MainViewModel viewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.viewModel().requestCrypto("btc");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listeners(){
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mainAdapter.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        /* Stop animation in shimmer layout */
        shimmerLayout.stopShimmerAnimation();
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
    public void showCoins(Crypto crypto) {
        /* Example shimmer layout - dismiss */
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();

        mainAdapter = new MainAdapter(this, crypto.ticker.markets);
        rvMain.setAdapter(mainAdapter);
        rvMain.setLayoutManager(Util.getLinearLayoutMangerDefault(this, true));
    }

    @Override
    public void showErrorOrEmptyList() {
        /* Example shimmer layout - dismiss */
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();

        llSearch.setVisibility(View.GONE);
        rvMain.setAdapter(new NoResultAdapter(this, "NO RESULT AVAILABLE"));
        rvMain.setLayoutManager(Util.getLinearLayoutMangerDefault(this, false));
    }
}