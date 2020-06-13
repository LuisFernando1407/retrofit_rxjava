package com.br.retrofit_rxjava.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.br.retrofit_rxjava.BR;
import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.databinding.DialogConfirmBinding;
import com.br.retrofit_rxjava.ui.dialog.base.BaseDialog;

public class ConfirmDialog extends BaseDialog<DialogConfirmBinding, ConfirmDialogViewModel>
        implements ConfirmDialogNavigator {

    public ConfirmDialog(@NonNull Activity activity){
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.viewModel().setNavigator(this);
    }

    @Override
    protected int bindingVariable() {
        return BR.viewModel;
    }

    @Override
    public ConfirmDialogViewModel viewModel() {
        return new ViewModelProvider((ViewModelStoreOwner) this.activity).get(ConfirmDialogViewModel.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_confirm;
    }

    @Override
    public void onDismiss() {
        dismiss();
    }
}
