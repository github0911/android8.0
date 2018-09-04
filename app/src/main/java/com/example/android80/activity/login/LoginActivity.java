package com.example.android80.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android80.R;
import com.example.android80.activity.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
