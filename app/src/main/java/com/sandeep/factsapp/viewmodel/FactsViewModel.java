package com.sandeep.factsapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sandeep.factsapp.network.RestClient;
import com.sandeep.factsapp.repository.FactsRepository;
import com.sandeep.factsapp.model.FactsModel;

import rx.Observable;


public class FactsViewModel extends AndroidViewModel {

    private final LiveData<FactsModel> factListObservable;

    public FactsViewModel(@NonNull Application application) {
        super(application);
        factListObservable = FactsRepository.getInstance().getFacts(application, RestClient.getRetrofitInterface(getApplication()).getFacts());
    }

    public LiveData<FactsModel> getFactListObservable() {
        return factListObservable;
    }

    public void getFacts() {
        FactsRepository.getInstance().getFacts(getApplication(), RestClient.getRetrofitInterface(getApplication()).getFacts());
    }

    public LiveData<FactsModel> getFacts(Observable<FactsModel> factsModelObservable) {
        return FactsRepository.getInstance().getFacts(getApplication(), factsModelObservable);
    }

}
