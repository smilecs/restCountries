package com.smile.restcountries.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smile.restcountries.R;
import com.smile.restcountries.model.CountryModel;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private List<CountryModel> models;

    public CountryAdapter(List<CountryModel> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CountryModel countryModel = models.get(i);
        viewHolder.countryName.setText(countryModel.getName());
        if (!countryModel.getCurrency().isEmpty()) {
            viewHolder.currency.setText(countryModel.getCurrency().get(0).getName());
        }
        if (!countryModel.getLanguages().isEmpty()) {
            viewHolder.language.setText(countryModel.getLanguages().get(0).getName());
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void removeItem(int position){
        models.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName, language, currency;
        public LinearLayout viewBackground, viewForeground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name_textview);
            language = itemView.findViewById(R.id.language_textview);
            currency = itemView.findViewById(R.id.currency_textview);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
