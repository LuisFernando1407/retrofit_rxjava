package com.br.retrofit_rxjava.util.observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.br.retrofit_rxjava.util.Debounce;

import java.util.concurrent.TimeUnit;

public class ObserverTextWatcher {

    private TextWatcher itemWatcher;
    private int debounceDelay;
    private ObserverTextWatcherCallback observerTextWatcherCallback;

    private ObserverTextWatcher(ObserverTextWatcherCallback observerTextWatcherCallback) {
        this.observerTextWatcherCallback = observerTextWatcherCallback;
        this.debounceDelay = 0;
        this.itemWatcher = this.getWatcher();
    }

    private ObserverTextWatcher(int debounceDelay, ObserverTextWatcherCallback observerTextWatcherCallback) {
        this.observerTextWatcherCallback = observerTextWatcherCallback;
        this.debounceDelay = debounceDelay;
        this.itemWatcher = this.getWatcher();
    }

    /* Method factory */
    public static ObserverTextWatcher of(ObserverTextWatcherCallback observerTextWatcherCallback) {
        return new ObserverTextWatcher(observerTextWatcherCallback);
    }

    /* Method factory */
    public static ObserverTextWatcher of(int debounceDelay, ObserverTextWatcherCallback observerTextWatcherCallback) {
        return new ObserverTextWatcher(debounceDelay, observerTextWatcherCallback);
    }

    private TextWatcher getWatcher() {
        return new TextWatcher() {
            final Debounce strategyDebounce = new Debounce();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.strategyDebounce.debounce(
                        Void.class,
                        () -> observerTextWatcherCallback.onSubmit(s.toString()),
                        debounceDelay,
                        TimeUnit.MILLISECONDS
                );
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    /* Method auxiliary to data binding (Get) */
    public TextWatcher getItemWatcher() {
        return itemWatcher;
    }

    /* Method auxiliary to data binding (Set) */
    public void setItemWatcher(TextWatcher itemWatcher) {
        this.itemWatcher = itemWatcher;
    }

    @BindingAdapter("textChangedListener")
    public static void bindTextWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }
}
