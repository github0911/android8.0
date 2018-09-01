package com.example.android80.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android80.R;
import com.example.android80.activity.base.BaseActivity;
import com.example.android80.api.MovieService;
import com.example.android80.entity.MovieEntity;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieActivity extends BaseActivity {

    private TextView tvMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initView();
        getMovie();
    }

    private void initView() {
        tvMovieTitle = findViewById(R.id.tv_movie_title);
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MovieActivity.class);
        return intent;
    }

    private void getMovie() {
        //https://gank.io/post/56e80c2c677659311bed9841
        String baseUrl = "https://api.douban.com/v2/movie/";

        //addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        //添加Retrofit对Rxjava 返回的就是一个Observable
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MovieService service = retrofit.create(MovieService.class);

        service.getTopMovie(0, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntity>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable  = d;
                        if (!d.isDisposed()) {
                            Logger.d("method %s", "onSubscribe");
                        }
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        toast(movieEntity.getTitle());
                        Logger.d("method %s", "onNext");
                        tvMovieTitle.setText(movieEntity.getSubjects().get(0).getTitle());
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        Logger.d("method %s", "onComplete");
                    }
                });
//        call.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                MovieEntity entity = response.body();
//                textView.setText(new Gson().toJson(entity.getTitle()));
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                textView.setText(t.getMessage());
//            }
//        });

    }
}
