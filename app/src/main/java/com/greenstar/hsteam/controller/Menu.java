package com.greenstar.hsteam.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.utils.Util;
import com.greenstar.hsteam.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity implements View.OnClickListener, WebserviceResponse {

        LinearLayout llSync;
        LinearLayout llBasket;
        LinearLayout llProfile;
        LinearLayout llDashboard;
        LinearLayout llQTVForm;
        ProgressDialog progressBar = null;
        AppDatabase db =null;
        Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        activity = this;
        db = AppDatabase.getAppDatabase(this);

        llDashboard = findViewById(R.id.llDashboard);
        llDashboard.setOnClickListener(this);

        llSync = findViewById(R.id.llSync);
        llSync.setOnClickListener(this);

        llBasket = findViewById(R.id.llBasket);
        llBasket.setOnClickListener(this);

        llProfile = findViewById(R.id.llProfile);
        llProfile.setOnClickListener(this);

        llQTVForm = findViewById(R.id.llForm);
        llQTVForm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.llDashboard){
            Toast.makeText(this,"Development in progress...",Toast.LENGTH_SHORT).show();

        }else if(v.getId()==R.id.llSync){
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

        }else if(v.getId()==R.id.llBasket){
            Intent myIntent = new Intent(this, SubmittedForms.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llProfile){
            Toast.makeText(this,"Development in progress...",Toast.LENGTH_SHORT).show();
        }else if(v.getId()==R.id.llForm){
            Intent myIntent = new Intent(activity, NewQTVForm.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void responseAlert(String response) {

        if(response.equals(Codes.TIMEOUT)){
            Toast.makeText(this, "Timeout Session - Could not connect to server. Please contact Admin",Toast.LENGTH_LONG).show();
        }else if(response.equals(Codes.SOMETHINGWENTWRONG)){
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
                e.printStackTrace();
            }
            if(Codes.ALL_OK.equals(status)){
                // db.getProvidersDAO().nukeTable();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        progressBar.dismiss();

    }
}
