package com.br.retrofit_rxjava.ui.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


public abstract class BaseDialog<B extends ViewDataBinding, V extends BaseDialogViewModel<?>> extends Dialog {

    public V viewModel;

    public B databinding;

    public Activity activity;

    public BaseDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.performDataBinding();
    }

    /**
     * Configure data bind with lifecycle of this Activity.
     */
    public void performDataBinding() {
        this.databinding = DataBindingUtil.inflate(
                LayoutInflater.from(this.activity),
                getLayoutResource(),
                null,
                false
        );

        setContentView(this.databinding.getRoot());

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
    public abstract V viewModel();

    /**
     * Layout resource.
     */
    @LayoutRes
    protected abstract int getLayoutResource();
}
