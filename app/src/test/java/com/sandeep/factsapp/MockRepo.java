package com.sandeep.factsapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.sandeep.factsapp.model.FactsModel;

import rx.Observable;

public class MockRepo {

    public Observable<FactsModel> getFactListObservable() {
        MutableLiveData<FactsModel> data = new MutableLiveData<>();
        FactsModel factsModel = new FactsModel();
        data.setValue(factsModel);
        return Observable.just(factsModel);
       // return data;
    }
}
