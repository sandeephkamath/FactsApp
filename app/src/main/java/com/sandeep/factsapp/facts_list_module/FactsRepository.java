package com.sandeep.factsapp.facts_list_module;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.sandeep.factsapp.R;
import com.sandeep.factsapp.model.Fact;
import com.sandeep.factsapp.model.FactsModel;
import com.sandeep.factsapp.network.RestClient;
import com.sandeep.factsapp.utils.Utils;

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


    public LiveData<FactsModel> getFacts(Context context) {
        final FactsModel factsModel = new FactsModel();
        factsModel.setState(FactsModel.LOADING);
        Subscriber<FactsModel> subscriber = getSubscriber(factsModel, context);
        RestClient.getRetrofitInterface(context).getFacts().subscribeOn(Schedulers.io())
                .map(new Func1<FactsModel, FactsModel>() {
                    @Override
                    public FactsModel call(FactsModel factsModel) {
                        //Remove invalid facts from the list
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
    private Subscriber<FactsModel> getSubscriber(final FactsModel factsModel, final Context context) {
        return new Subscriber<FactsModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                factsModel.setState(FactsModel.ERROR);
                if (Utils.isNetworkAvailable(context)) {
                    factsModel.setErrorMessage(context.getString(R.string.no_results_found));
                } else {
                    factsModel.setErrorMessage(context.getString(R.string.no_internet_message));
                }
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
