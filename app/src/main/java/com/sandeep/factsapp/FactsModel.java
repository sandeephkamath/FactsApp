package com.sandeep.factsapp;

import com.sandeep.factsapp.model.Fact;

import java.util.List;

public class FactsModel {

    public static final int LOADING = 0;
    public static final int ERROR = 1;
    public static final int SUCCESS = 2;

    private int state;
    private List<Fact> facts;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }
}
