package com.br.retrofit_rxjava.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.view.Window;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.RetrofitRxJavaApplication;

import java.util.Objects;

public class CommonUtils {

    private static SharedPreferences getSessionPreferences() {
        Context ctx = RetrofitRxJavaApplication.of().getContext();
        return ctx.getSharedPreferences("SESSION_PREFERENCES", Context.MODE_PRIVATE);
    }

    public static RecyclerView.LayoutManager getLinearLayoutMangerDefault(Context context, boolean scroll){
        return new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return scroll;
            }
        };
    }

    public static String getApiToken() {
        return getSessionPreferences().getString("API_TOKEN", null);
    }

    public static void setApiToken(String token) {
        SharedPreferences mPreferences = getSessionPreferences();
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("API_TOKEN", token);
        editor.apply();
    }

    public static Dialog loadingDialog(final Context ctx) {
        Dialog loading = new Dialog(ctx);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        Objects.requireNonNull(loading.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);
        return loading;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void alert(Context ctx, String title, String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctx);
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(Html.fromHtml(message));
        alertDialog.show();
    }
}
