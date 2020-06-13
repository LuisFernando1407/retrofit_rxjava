package com.br.retrofit_rxjava.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.br.retrofit_rxjava.R;

public class NoResultAdapter extends RecyclerView.Adapter<NoResultAdapter.NoResultHolder>{

    private Context context;
    private String title;

    private NoResultAdapter(Context context, String title){
        this.context = context;
        this.title = title;
    }

    public static NoResultAdapter of(Context context, String title){
        return new NoResultAdapter(context, title);
    }

    @NonNull
    @Override
    public NoResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_no_result, parent, false);
        return new NoResultAdapter.NoResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoResultHolder holder, int position) {
        holder.tvTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class NoResultHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvTitle;

        NoResultHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
