package com.sandeep.factsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class FactsViewModel extends AndroidViewModel {

    private final LiveData<FactsModel> factListObservable;

    public FactsViewModel(@NonNull Application application) {
        super(application);
        factListObservable = FactsRepository.getInstance().getFacts();
    }

    public LiveData<FactsModel> getFactListObservable() {
        return factListObservable;
    }

}
