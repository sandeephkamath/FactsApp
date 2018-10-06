package com.sandeep.factsapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sandeep.factsapp.databinding.FactHolderBinding;
import com.sandeep.factsapp.model.Fact;

import java.util.List;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.FactHolder> {

    private List<Fact> facts;

    FactAdapter(List<Fact> facts) {
        this.facts = facts;
    }


    @NonNull
    @Override
    public FactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FactHolderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fact_holder, parent, false);
        return new FactHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FactHolder holder, int position) {
        holder.binding.setFact(facts.get(position));
    }

    @Override
    public int getItemCount() {
        if (null != facts) {
            return facts.size();
        }
        return 0;
    }

    class FactHolder extends RecyclerView.ViewHolder {

        private final FactHolderBinding binding;

        FactHolder(FactHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
