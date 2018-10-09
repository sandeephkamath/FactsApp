package com.sandeep.factsapp.facts_list_module;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sandeep.factsapp.R;
import com.sandeep.factsapp.model.FactsModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsListActivity extends AppCompatActivity {

    @BindView(R.id.facts_list_view)
    RecyclerView factsListView;

    @BindView(R.id.progress_indicator)
    ProgressBar progressBar;

    @BindView(R.id.no_result_view)
    TextView noResultsView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FactsViewModel viewModel;
    private FactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupFactsRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                factsListView.setVisibility(View.GONE);
                viewModel.getFacts();
            }
        });
        observeViewModel();
    }

    private void setupFactsRecyclerView() {
        factsListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FactAdapter();
        factsListView.setAdapter(adapter);
        // factsListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                            onSuccess(factsModel);
                            break;
                        case FactsModel.ERROR:
                            onError(factsModel);
                            break;
                    }
                }
            }
        });
    }

    private void onError(FactsModel factsModel) {
        noResultsView.setText(factsModel.getErrorMessage());
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        factsListView.setVisibility(View.GONE);
        noResultsView.setVisibility(View.VISIBLE);
    }

    private void onSuccess(FactsModel factsModel) {
        swipeRefreshLayout.setRefreshing(false);
        noResultsView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        factsListView.setVisibility(View.VISIBLE);
        adapter.setData(factsModel.getFacts());
        toolbar.setTitle(factsModel.getTitle());
    }

}
