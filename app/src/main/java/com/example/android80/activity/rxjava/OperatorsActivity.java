package com.example.android80.activity.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android80.R;
import com.example.android80.activity.base.BaseActivity;
import com.example.android80.activity.rxjava.operators.DisposableExampleActivity;


public class OperatorsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);
    }

    public void startDisposableActivity(View view) {
        startActivity(new Intent(this, DisposableExampleActivity.class));
    }

}
