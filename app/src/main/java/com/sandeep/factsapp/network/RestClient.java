package com.sandeep.factsapp.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sandeep.factsapp.utils.Constants;
import com.sandeep.factsapp.utils.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static RetrofitInterface getRetrofitInterface(final Context context) {

        OkHttpClient client = getOkHttpClient(context);

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(RetrofitInterface.class);
    }

    @NonNull
    private static OkHttpClient getOkHttpClient(final Context context) {

        Cache cache = null;
        if (context != null) {
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MB
            cache = new Cache(httpCacheDirectory, cacheSize);
        }

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                //Cache header implementation.
                int maxStale = 60 * 60 * 24 * 28; // 4 weeks
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxStale)
                        .build();
            }
        });
        if (cache != null) {
            builder.cache(cache);
        }
        return builder.build();
    }


}
