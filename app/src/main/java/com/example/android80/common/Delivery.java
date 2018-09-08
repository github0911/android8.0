package com.example.android80.common;

import android.os.Handler;
import android.os.Looper;

public class Delivery {
    private static Delivery sInstance;

    public static Delivery getInstance() {
        if (sInstance == null) {
            synchronized (Delivery.class) {
                if (sInstance == null) {
                    sInstance = new Delivery();
                }
            }
        }
        return sInstance;
    }

    private Handler mHandler;

    public Delivery() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void post(Runnable post) {
        mHandler.post(post);
    }

    public void postDelayed(Runnable post, long delay) {
        mHandler.postDelayed(post, delay);
    }
}
