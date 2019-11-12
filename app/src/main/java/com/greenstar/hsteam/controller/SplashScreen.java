package com.greenstar.hsteam.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FirebaseMessaging.getInstance().subscribeToTopic("updateMapping");
        FirebaseMessaging.getInstance().subscribeToTopic("syncAll");
        FirebaseMessaging.getInstance().subscribeToTopic("generalMessage");

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = (shared.getBoolean("isLoggedIn", false));
        if(isLoggedIn){
            Intent intent = new Intent(getApplicationContext(),
                    Menu.class);

            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this,
                    LoginScreen.class);

            startActivity(intent);
            finish();
        }


    }

}
