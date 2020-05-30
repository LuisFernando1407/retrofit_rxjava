package com.br.retrofit_rxjava.ui.activity.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.br.retrofit_rxjava.ui.activity.FlowNavigator;
import com.br.retrofit_rxjava.util.CommonUtils;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModel<?>> extends AppCompatActivity implements FlowNavigator {

    private Dialog dialog;
    public V viewModel;

    public B databinding;

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

    public void showLoading(boolean show){
        if(show){
            dialog.show();
        }else{
            if(dialog.isShowing()) dialog.dismiss();
        }
    }

    /**
     * Configure data bind with lifecycle of this Activity.
     */
    public void performDataBinding(){
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
}