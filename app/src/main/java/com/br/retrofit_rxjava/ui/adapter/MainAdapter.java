package com.br.retrofit_rxjava.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.br.retrofit_rxjava.R;
import com.br.retrofit_rxjava.data.model.Market;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Market market = marketsFiltered.get(position);

        holder.tvMarket.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        holder.tvMarket.setOnClickListener(e -> Toast.makeText(context, "Market name", Toast.LENGTH_SHORT).show());
        holder.tvMarket.setText(market.market.toUpperCase());

        holder.tvVolume.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        holder.tvVolume.setOnClickListener(e -> Toast.makeText(context, "Volume of " + market.volume, Toast.LENGTH_SHORT).show());
        holder.tvVolume.setText(String.valueOf(market.volume));

        holder.tvPrice.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        @SuppressLint("DefaultLocale") String priceFormat = "$" + String.format("%.2f", Double.parseDouble(market.price));
        holder.tvPrice.setText(priceFormat);
        holder.tvPrice.setOnClickListener(e -> Toast.makeText(context, "Price of " + priceFormat, Toast.LENGTH_SHORT).show());
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

        private AppCompatTextView tvVolume;
        private AppCompatTextView tvMarket;
        private AppCompatTextView tvPrice;

        ViewHolder(View view) {
            super(view);

            tvVolume = view.findViewById(R.id.tvVolume);
            tvMarket = view.findViewById(R.id.tvMarket);
            tvPrice = view.findViewById(R.id.tvPrice);
        }
    }
}