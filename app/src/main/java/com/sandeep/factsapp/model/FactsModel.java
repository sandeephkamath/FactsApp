package com.sandeep.factsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sandeep.factsapp.model.Fact;

import java.util.List;

public class FactsModel {

    public static final int LOADING = 0;
    public static final int ERROR = 1;
    public static final int SUCCESS = 2;

    private int state;
    @SerializedName("rows")
    @Expose
    private List<Fact> facts;
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
