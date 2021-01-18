package com.greenstar.hsteam.controller.qtv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.greenstar.hsteam.R;
import com.greenstar.hsteam.controller.Codes;
import com.greenstar.hsteam.controller.PartialSync;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.utils.Util;
import com.greenstar.hsteam.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.fabric.sdk.android.Fabric;

public class QTVMenu extends AppCompatActivity implements View.OnClickListener, WebserviceResponse, View.OnLongClickListener {

        LinearLayout llSync;
        LinearLayout llBasket;
        LinearLayout llProfile;
        LinearLayout llDashboard;
        LinearLayout llQTVForm;
        LinearLayout llPartialSynchronization;
        ProgressDialog progressBar = null;
        AppDatabase db =null;
        Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Fabric.with(this, new Crashlytics());

        activity = this;
        db = AppDatabase.getAppDatabase(this);

        llDashboard = findViewById(R.id.llDashboard);
        llDashboard.setOnClickListener(this);

        llSync = findViewById(R.id.llSync);
        llSync.setOnClickListener(this);
        llSync.setOnLongClickListener(this);

        llBasket = findViewById(R.id.llBasket);
        llBasket.setOnClickListener(this);

        llProfile = findViewById(R.id.llApprovalStatus);
        llProfile.setOnClickListener(this);

        llQTVForm = findViewById(R.id.llForm);
        llQTVForm.setOnClickListener(this);

        llPartialSynchronization = findViewById(R.id.llPartialSynchronization);
        llPartialSynchronization.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences editor = this.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        boolean updateMapping = editor.getBoolean("updateMapping",false);
        boolean syncAll = editor.getBoolean("syncAll",false);

        if(updateMapping && Util.isNetworkAvailable(this)){
            Util util = new Util();
            util.setResponseListener(this);
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Perform Sync ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar

            util.pullMapping(this);

            SharedPreferences.Editor edit = editor.edit();
            edit.putBoolean("updateMapping", false);
            edit.apply();
        }
        if(syncAll && Util.isNetworkAvailable(this)){
            Util util = new Util();
            util.setResponseListener(this);
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Perform Sync ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();//displays the progress bar
            util.performSync(this);

            SharedPreferences.Editor edit = editor.edit();
            edit.putBoolean("syncAll", false);
            edit.apply();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.llDashboard){
/*
           Intent myIntent = new Intent(this, DashboardController.class);
            startActivity(myIntent);
            */
        Toast.makeText(this,"Under development", Toast.LENGTH_SHORT).show();

        }else if(v.getId()==R.id.llSync){
            try{
                if(Util.isNetworkAvailable(this)){
                    Util util = new Util();
                    util.setResponseListener(this);
                    progressBar = new ProgressDialog(this);
                    progressBar.setCancelable(false);//you can cancel it by pressing back button
                    progressBar.setMessage("Perform Sync ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();//displays the progress bar
                    util.performSync(this);
                }else{
                    Toast.makeText(this,"Please connect to the internet service and try again.", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Crashlytics.logException(e);
            }

        }else if(v.getId()==R.id.llBasket){
            Intent myIntent = new Intent(this, SubmittedForms.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llApprovalStatus){
            Intent myIntent = new Intent(this, ApprovalStatus.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llForm){
            if(db!=null){
                SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
                String region = prefs.getString("AMCode", "");
                int count = db.getProvidersDAO().getCount();

                if(count==0 || region == null || region == ""){
                    Toast.makeText(this, "Kindly Sync providers and Basic info from Partial Sync option", Toast.LENGTH_SHORT).show();
                }else{
                    Intent myIntent = new Intent(activity, NewQTVForm.class);
                    startActivity(myIntent);
                }
            }

        }else if(v.getId()==R.id.llPartialSynchronization){
            Intent myIntent = new Intent(activity, PartialSync.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void responseAlert(String response) {
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String name = prefs.getString("name", "");
        if (name == null) {

            name="";
        }
        if(response.equals(Codes.TIMEOUT)){
            Crashlytics.log(Log.ERROR, name,  " Timeout session at "+new Date());
            Toast.makeText(this, "Timeout Session - Could not connect to server. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.SOMETHINGWENTWRONG)){
            Crashlytics.log(Log.ERROR, name," Something went wrong at "+new Date());
            Toast.makeText(this, "Something went wrong. Please contact Admin",Toast.LENGTH_LONG).show();
        }else{
            JSONObject responseObj=null;
            String status="";
            String message = "";
            String data="";

            try {
                responseObj = new JSONObject(response);
                status=responseObj.get("status").toString();
                message=responseObj.get("message").toString();
                data=responseObj.get("data").toString();
            } catch (JSONException e) {
                Crashlytics.logException(e);
            }
            if(Codes.ALL_OK.equals(status)){
                // db.getProvidersDAO().nukeTable();
            }
            Crashlytics.log(Log.ERROR, name, " Sync Successful at "+new Date());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        progressBar.dismiss();

    }

    @Override
    public boolean onLongClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("syncData", Util.getCTSSyncData(this));
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,"All forms copied!", Toast.LENGTH_LONG).show();
        return false;
    }
}
