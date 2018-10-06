package com.sandeep.factsapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.sandeep.factsapp.model.Fact;
import com.sandeep.factsapp.network.RestClient;

import java.util.ArrayList;
import java.util.Iterator;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FactsRepository {

    private static FactsRepository INSTANCE;
    private MutableLiveData<FactsModel> data;

    private FactsRepository() {
        data = new MutableLiveData<>();
    }

    public static FactsRepository getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new FactsRepository();
        }
        return INSTANCE;
    }


    public LiveData<FactsModel> getFacts() {
        final FactsModel factsModel = new FactsModel();
        factsModel.setState(FactsModel.LOADING);
        Subscriber<FactsModel> subscriber = getSubscriber(factsModel);
        RestClient.getRetrofitInterface().getFacts().subscribeOn(Schedulers.io())
                .map(new Func1<FactsModel, FactsModel>() {
                    @Override
                    public FactsModel call(FactsModel factsModel) {
                        for (Iterator<Fact> iterator = factsModel.getFacts().listIterator(); iterator.hasNext(); ) {
                            Fact fact = iterator.next();
                            if (fact.isInvalid()) {
                                iterator.remove();
                            }
                        }
                        return factsModel;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        data.setValue(factsModel);
        return data;
    }

    @NonNull
    private Subscriber<FactsModel> getSubscriber(final FactsModel factsModel) {
        return new Subscriber<FactsModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                factsModel.setState(FactsModel.ERROR);
                data.postValue(factsModel);
            }

            @Override
            public void onNext(FactsModel factsModel1) {
                factsModel.setState(FactsModel.SUCCESS);
                factsModel.setTitle(factsModel1.getTitle());
                factsModel.setFacts(factsModel1.getFacts());
                data.postValue(factsModel);
            }
        };
    }

}
