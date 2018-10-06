package com.sandeep.factsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

public class ListActivity extends AppCompatActivity {

    RecyclerView factsListView;
    FactAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        factsListView = findViewById(R.id.facts_list_view);
        progressBar = findViewById(R.id.progress_indicator);
        factsListView.setLayoutManager(new LinearLayoutManager(this));

        observeViewModel();

    }

    private void observeViewModel() {
        final FactsViewModel viewModel =
                ViewModelProviders.of(this).get(FactsViewModel.class);

        viewModel.getFactListObservable().observe(this, new Observer<FactsModel>() {
            @Override
            public void onChanged(@Nullable FactsModel factsModel) {
                if (null != factsModel) {
                    switch (factsModel.getState()) {
                        case FactsModel.LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case FactsModel.SUCCESS:
                            progressBar.setVisibility(View.GONE);
                            adapter = new FactAdapter(factsModel.getFacts());
                            factsListView.setAdapter(adapter);
                            break;
                    }
                }
            }
        });
    }

}
