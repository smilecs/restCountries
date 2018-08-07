package com.smile.restcountries;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.smile.restcountries.adapter.CountryAdapter;
import com.smile.restcountries.model.CountryModel;
import com.smile.restcountries.widget.RecyclerTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerTouchHelper.TouchHelpListener {
    private CountryViewModel countryViewModel;
    private CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        countryAdapter = new CountryAdapter(countryViewModel.getCountryModelList());
        RecyclerView recyclerView = findViewById(R.id.content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final ProgressBar progressBar = findViewById(R.id.progress);
        if (savedInstanceState == null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        //added custom item decoration so list line will be skipped on the last item
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(countryAdapter);
        RecyclerTouchHelper itemtouchCallback = new RecyclerTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemtouchCallback).attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(itemtouchCallback);
        countryViewModel.getCountryLiveData().observe(this, new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(@Nullable List<CountryModel> models) {
                progressBar.setVisibility(View.GONE);
                if (models != null && !models.isEmpty()) {
                    countryViewModel.getCountryModelList().clear();
                    countryViewModel.getCountryModelList().addAll(models);
                    countryAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(findViewById(R.id.container), getString(R.string.error_case),
                          Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder instanceof CountryAdapter.ViewHolder) {
            countryAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
