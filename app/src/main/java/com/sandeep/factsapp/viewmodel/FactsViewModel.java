package com.sandeep.factsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sandeep.factsapp.repository.FactsRepository;
import com.sandeep.factsapp.model.FactsModel;

public class FactsViewModel extends AndroidViewModel {

    private final LiveData<FactsModel> factListObservable;

    public FactsViewModel(@NonNull Application application) {
        super(application);
        factListObservable = FactsRepository.getInstance().getFacts(application);
    }

    public LiveData<FactsModel> getFactListObservable() {
        return factListObservable;
    }

    public void getFacts() {
        FactsRepository.getInstance().getFacts(getApplication());
    }
}
