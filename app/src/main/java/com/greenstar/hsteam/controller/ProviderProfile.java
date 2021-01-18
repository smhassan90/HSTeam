package com.greenstar.hsteam.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.greenstar.hsteam.R;
import com.greenstar.hsteam.db.AppDatabase;

public class ProviderProfile  extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog = null;
    AppDatabase db =null;
    Activity activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        db = AppDatabase.getAppDatabase(this);

        setContentView(R.layout.activity_provider_profile);
    }

    @Override
    public void onClick(View v) {

    }
}
