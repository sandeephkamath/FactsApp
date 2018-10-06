package com.sandeep.factsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sandeep.factsapp.model.FactsModel;

public class ListActivity extends AppCompatActivity {

    RecyclerView factsListView;
    FactAdapter adapter;
    ProgressBar progressBar;
    TextView noResultsView;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    private FactsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        factsListView = findViewById(R.id.facts_list_view);
        progressBar = findViewById(R.id.progress_indicator);
        factsListView.setLayoutManager(new LinearLayoutManager(this));
        noResultsView = findViewById(R.id.no_result_view);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        adapter = new FactAdapter();
        factsListView.setAdapter(adapter);
        factsListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                factsListView.setVisibility(View.GONE);
                adapter.clear();
                viewModel.getFacts();
            }
        });
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel = ViewModelProviders.of(this).get(FactsViewModel.class);

        viewModel.getFactListObservable().observe(this, new Observer<FactsModel>() {
            @Override
            public void onChanged(@Nullable FactsModel factsModel) {
                if (null != factsModel) {
                    switch (factsModel.getState()) {
                        case FactsModel.LOADING:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case FactsModel.SUCCESS:
                            swipeRefreshLayout.setRefreshing(false);
                            noResultsView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            factsListView.setVisibility(View.VISIBLE);
                            adapter.setData(factsModel.getFacts());
                            toolbar.setTitle(factsModel.getTitle());
                            break;
                        case FactsModel.ERROR:
                            swipeRefreshLayout.setRefreshing(false);
                            progressBar.setVisibility(View.GONE);
                            factsListView.setVisibility(View.GONE);
                            noResultsView.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
    }

}
