package com.sandeep.factsapp.network;

import com.sandeep.factsapp.model.FactsModel;

import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<FactsModel> getFacts();

}
