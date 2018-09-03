package com.example.android80.api.common;

import com.example.android80.api.MovieService;
import com.example.android80.entity.MovieEntity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethod {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    public static final int TIME_OUT = 15;

    private Retrofit retrofit;

    private MovieService movieService;

    private HttpMethod() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    private static class SingleHolder {
        private static final HttpMethod INSTANCE = new HttpMethod();
    }

    public static HttpMethod getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void getMovie(Observer<MovieEntity> observer, int start, int count) {

        movieService.getTopMovie(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
