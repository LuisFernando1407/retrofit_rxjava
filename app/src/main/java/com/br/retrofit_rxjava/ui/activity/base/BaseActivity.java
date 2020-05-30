package com.br.retrofit_rxjava.ui.activity.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.br.retrofit_rxjava.ui.activity.FlowNavigator;
import com.br.retrofit_rxjava.util.Util;

import butterknife.ButterKnife;

public abstract class BaseActivity<V extends BaseViewModel<?>> extends AppCompatActivity implements FlowNavigator {

    private Dialog dialog;
    private V viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        this.initializeButterKnife();

        this.viewModel = this.viewModel();
        this.dialog = Util.loadingDialog(this);
    }

    private void initializeButterKnife(){
        ButterKnife.bind(this);
    }

    protected abstract V viewModel();

    protected abstract int getLayoutResource();

    public void showLoading(boolean show){
        if(show){
            dialog.show();
        }else{
            if(dialog.isShowing()) dialog.dismiss();
        }
    }

    @Override
    public void onAlertError(String title, String message) {
        //TODO: Implement
    }

    @Override
    public void onAlertSuccess(String title, String message) {
        //TODO: Implement
    }
}