package com.greenstar.hsteam.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.greenstar.hsteam.controller.Codes;
import com.greenstar.hsteam.dal.HSData;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.ApprovalQTVForm;
import com.greenstar.hsteam.model.QTVForm;
import com.greenstar.hsteam.model.SyncObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class Util {
   WebserviceResponse responseListener;

    public static void saveData(JSONObject params, Context activity){
        AppDatabase db =null;

        String data = "";
        String status = "";
        String approvalStatus= "";
        try{
            data = (String) params.get("data");
            status = (String) params.get("status");
        }catch(Exception e){
            Crashlytics.logException(e);
        }

        if(Codes.ALL_OK.equals(status)){
            HSData dataObj = new Gson().fromJson(data, HSData.class) ;

            SharedPreferences.Editor editor =  activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();
            editor.putString("name", dataObj.getName());
            editor.putString("AMName", dataObj.getAMName());
            editor.putString("AMCode", dataObj.getAMCode());
            editor.putString("region", dataObj.getRegion());
            editor.apply();
            try{
                db = AppDatabase.getAppDatabase(activity);
                db.getProvidersDAO().nukeTable();
                db.getApprovalQTVFormDAO().nukeTable();
                db.getDashboardDAO().nukeTable();
                db.getProvidersDAO().insertMultiple(dataObj.getProviders());
                if (dataObj.getQtvForms()!=null && dataObj.getQtvForms().size()>0){
                    db.getApprovalQTVFormDAO().insertMultiple(dataObj.getQtvForms());
                }
                if (dataObj.getDashboard()!=null ){
                    db.getDashboardDAO().insert(dataObj.getDashboard());
                }
            }catch(Exception e){
                Toast.makeText(activity,"Something went wrong. Please sync later",Toast.LENGTH_SHORT).show();
                Crashlytics.logException(e);
                Crashlytics.log("Sync Issue at "+ new Date());
            }
        }
    }
    public void pullMapping(final Context context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);


        SyncObject syncObject = new SyncObject();

        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);


        HttpUtils.get("hssync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String data = "";
                String codeReceived = "";
                String staffName = "";
                JSONObject params = new JSONObject();
                List<Integer> successfulIDs = new ArrayList<>();
                List<Integer> rejectedIDs = new ArrayList<>();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    staffName = response.get("staffName").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);

                    for(int i=0;i<response.getJSONArray("rejectedIDs").length();i++){
                        rejectedIDs.add(response.getJSONArray("rejectedIDs").getInt(i));
                    }
                    for(int i=0;i<response.getJSONArray("successfulIDs").length();i++){
                        successfulIDs.add(response.getJSONArray("successfulIDs").getInt(i));
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.logException(e);
                    Crashlytics.log("Sync Issue on pulling mapping at "+ new Date());
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    saveData(params, context);
                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Crashlytics.log("Sync successful but not handled  "+ new Date());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseListener.responseAlert(Codes.SOMETHINGWENTWRONG);
                Crashlytics.log("Sync Issue in pulling mapping on Failure "+ new Date());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseListener.responseAlert(Codes.TIMEOUT);
                Crashlytics.log("Sync Issue on pulling mapping on Timeout  "+ new Date());
            }
        });

    }

    public void performSync(final Activity context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);

        AppDatabase db = AppDatabase.getAppDatabase(context);

        List<QTVForm> qtvForms = db.getQTVFormDAO().getAllPendingForms();
        SyncObject syncObject = new SyncObject();
        syncObject.setQtvForms(qtvForms);

        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);

        HttpUtils.get("hssync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String data = "";
                String codeReceived = "";
                String staffName = "";
                JSONObject params = new JSONObject();
                List<Integer> successfulIDs = new ArrayList<>();
                List<Integer> rejectedIDs = new ArrayList<>();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    staffName = response.get("staffName").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);

                    for(int i=0;i<response.getJSONArray("rejectedIDs").length();i++){
                        rejectedIDs.add(response.getJSONArray("rejectedIDs").getInt(i));
                    }
                    for(int i=0;i<response.getJSONArray("successfulIDs").length();i++){
                        successfulIDs.add(response.getJSONArray("successfulIDs").getInt(i));
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                    Crashlytics.log("Sync Issue at "+ new Date());
                    Crashlytics.logException(e);
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    updateData(successfulIDs, rejectedIDs, context);
                    saveData(params, context);
                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Crashlytics.log("Sync Successful but not handled at "+ new Date());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseListener.responseAlert(Codes.SOMETHINGWENTWRONG);
                Crashlytics.log("Sync failed and got Something went wrong at  "+ new Date());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseListener.responseAlert(Codes.TIMEOUT);
                Crashlytics.log("Sync failed and Timeout at "+ new Date());
            }
        });
    }

    private void updateData(List<Integer> successfulIDs, List<Integer> rejectedIDs, Activity activity) {
        AppDatabase db = AppDatabase.getAppDatabase(activity);
        try {
            for (int id : successfulIDs) {
                db.getQTVFormDAO().markQTVSuccessful(id);
            }
            for (int id : rejectedIDs) {
                db.getQTVFormDAO().markQTVRejected(id);
            }
        }catch(Exception e){
            Crashlytics.logException(e);
        }
    }

    public WebserviceResponse getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(WebserviceResponse responseListener) {
        this.responseListener = responseListener;
    }

    public static int getNextQTVFormID(Activity activity){
        SharedPreferences editor = activity.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        int qtvFormID =  editor.getInt("qtvFormID",0);
        qtvFormID++;
        SharedPreferences.Editor edit =editor.edit();
        edit.putInt("qtvFormID",qtvFormID);
        edit.apply();

        return qtvFormID;
    }

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}