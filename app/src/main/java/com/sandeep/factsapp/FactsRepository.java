package com.sandeep.factsapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.sandeep.factsapp.model.Fact;

import java.util.ArrayList;

public class FactsRepository {

    private static FactsRepository INSTANCE;

    private FactsRepository() {

    }

    public static FactsRepository getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new FactsRepository();
        }
        return INSTANCE;
    }


    public LiveData<FactsModel> getFacts() {
        final MutableLiveData<FactsModel> data = new MutableLiveData<>();
        final FactsModel factsModel = new FactsModel();
        factsModel.setState(FactsModel.LOADING);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Fact> facts = getDummyFacts();
                factsModel.setFacts(facts);
                factsModel.setState(FactsModel.SUCCESS);
                data.postValue(factsModel);
            }
        }, 3000);
        data.setValue(factsModel);
        return data;
    }

    @NonNull
    private ArrayList<Fact> getDummyFacts() {
        ArrayList<Fact> facts = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            Fact fact = new Fact();
            fact.setTitle("Title " + i);
            fact.setDescription("Description " + i);
            fact.setImageHref("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg");
            facts.add(fact);
        }
        return facts;
    }

}
