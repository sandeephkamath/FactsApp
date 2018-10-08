package com.sandeep.factsapp.model;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class FactDiffCallback extends DiffUtil.Callback {

    private List<Fact> oldFacts;
    private List<Fact> newFacts;

    public FactDiffCallback(List<Fact> oldFacts, List<Fact> newFacts) {
        this.newFacts = newFacts;
        this.oldFacts = oldFacts;
    }


    @Override
    public int getOldListSize() {
        return oldFacts.size();
    }

    @Override
    public int getNewListSize() {
        return newFacts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newFacts.get(newItemPosition).isItemSame(oldFacts.get(oldItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newFacts.get(newItemPosition).isContentSame(oldFacts.get(oldItemPosition));
    }
}
