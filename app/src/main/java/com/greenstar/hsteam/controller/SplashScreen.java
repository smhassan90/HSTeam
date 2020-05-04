package com.greenstar.hsteam.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.greenstar.hsteam.controller.qat.QATMenu;
import com.greenstar.hsteam.controller.qtv.QTVMenu;

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
        int isQATAllowed = (shared.getInt("isQATAllowed", 0));
        int isQTVAllowed = (shared.getInt("isQTVAllowed", 0));
        if(isLoggedIn){
            if(isQTVAllowed==1 && isQATAllowed==1){
                Intent intent = new Intent(getApplicationContext(),
                        FormMenu.class);

                startActivity(intent);
                finish();
            }else if(isQTVAllowed==0 && isQATAllowed==1){
                Intent intent = new Intent(getApplicationContext(),
                        QATMenu.class);

                startActivity(intent);
                finish();
            }else if(isQTVAllowed==1 && isQATAllowed==0){
                Intent intent = new Intent(getApplicationContext(),
                        QTVMenu.class);

                startActivity(intent);
                finish();
            }
        }else{
            Intent intent = new Intent(this,
                    LoginScreen.class);

            startActivity(intent);
            finish();
        }
    }
}
