package com.br.retrofit_rxjava.ui.activity.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.data.model.Market;
import com.br.retrofit_rxjava.databinding.ItemMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Market> markets;
    private List<Market> marketsFiltered;

    private MainAdapter(Context context, List<Market> markets){
        this.context = context;
        this.markets = markets;
        this.marketsFiltered = markets;
    }

    /* Method factory */
    public static MainAdapter of(Context context, List<Market> markets){
        return new MainAdapter(context, markets);
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainBinding itemMainBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_main,
                parent,
                false
        );
        return new MainAdapter.ViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        MainAdapterUtil mainAdapterUtil = MainAdapterUtil.of(
                holder,
                context,
                marketsFiltered.get(position)
        );

        mainAdapterUtil.changeColorAndSetData();
    }

    @Override
    public int getItemCount() {
        return marketsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressLint("DefaultLocale")
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    marketsFiltered = markets;
                } else {
                    List<Market> filteredList = new ArrayList<>();
                    for (Market row : markets) {
                        if (row.market.toLowerCase().equals(charString.toLowerCase()) ||
                                String.format("%.2f", Double.parseDouble(row.price)).contentEquals(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    marketsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = marketsFiltered;
                return filterResults;
            }

            @SuppressLint("UNCHECKED_CAST")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                marketsFiltered = (ArrayList<Market>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemMainBinding itemMainBinding;

        ViewHolder(@NonNull ItemMainBinding itemMainBinding) {
            super(itemMainBinding.getRoot());
            this.itemMainBinding = itemMainBinding;
        }
    }
}
