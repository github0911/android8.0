package com.example.android80.activity.rxjava.operators;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.example.android80.R;
import com.example.android80.activity.base.BaseActivity;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DisposableExampleActivity extends BaseActivity implements View.OnClickListener {

    TextView text;

    CompositeDisposable disposable = new CompositeDisposable();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposable_example);
        text = findViewById(R.id.tv_text);
        findViewById(R.id.tv_do_some_thing).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_do_some_thing: {

                doSomeWork();
                break;
            }

            default:
                break;
        }

    }

    private void doSomeWork() {
        disposable.add(sampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        text.append(" onNext ");
                        text.append(s);
                        text.append(" \n ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        text.append(" onError " + e.getMessage());
                        text.append(" \n ");
                    }

                    @Override
                    public void onComplete() {
                        text.append(" onComplete ");
                        text.append(" \n ");
                    }
                }));
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                SystemClock.sleep(1000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
