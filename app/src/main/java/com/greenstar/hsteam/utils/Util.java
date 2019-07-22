package com.greenstar.hsteam.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greenstar.hsteam.controller.Codes;
import com.greenstar.hsteam.dal.HSData;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.QTVForm;
import com.greenstar.hsteam.model.SyncObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class Util {
   WebserviceResponse responseListener;

    public static void saveData(JSONObject params, Activity activity){
        AppDatabase db =null;

        String data = "";
        String status = "";
        try{
            data = (String) params.get("data");
            status = (String) params.get("status");
        }catch(Exception e){
            Log.e("Error","Error while saving data after login");
        }

        if(Codes.ALL_OK.equals(status)){
            HSData dataObj = new Gson().fromJson(data, HSData.class) ;

            SharedPreferences.Editor editor =  activity.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();
            editor.putString("name", dataObj.getName());
            editor.putString("AMName", dataObj.getAMName());
            editor.putString("region", dataObj.getRegion());
            editor.apply();
            try{
                db = AppDatabase.getAppDatabase(activity);
                db.getProvidersDAO().nukeTable();
                db.getProvidersDAO().insertMultiple(dataObj.getProviders());
            }catch(Exception e){
                Toast.makeText(activity,"Something went wrong. Please sync later",Toast.LENGTH_SHORT).show();
            }finally {
            }

        }

    }
    /*
    public static String saveDataFromJSON(String data, Context context){


        Data dataRetrieved = new Data();

        try {
            dataRetrieved = new Gson().fromJson(data, Data.class);
        }
        catch(Exception e){

        }
        if(dataRetrieved!=null){
            AppDatabase db = AppDatabase.getAppDatabase(context);
            db.getUnapprovedSDCustomerDAO().nukeTable();
            if(dataRetrieved.getCustomers()!=null) {
                db.getCustomerDAO().nukeTable();
                db.getCustomerDAO().insertMultiple(dataRetrieved.getCustomers());
            }

            if(dataRetrieved.getDepots()!=null) {
                db.getDepotDAO().nukeTable();
                db.getDepotDAO().insertMultiple(dataRetrieved.getDepots());
            }

            if(dataRetrieved.getTowns()!=null) {
                db.getTownDAO().nukeTable();
                db.getTownDAO().insertMultiple(dataRetrieved.getTowns());
            }

            if(dataRetrieved.getTownDepots()!=null) {
                db.getTownDepotDAO().nukeTable();
                db.getTownDepotDAO().insertMultiple(dataRetrieved.getTownDepots());
            }

            if(dataRetrieved.getTownCustomers()!=null) {
                db.getTownCustomerDAO().nukeTable();
                db.getTownCustomerDAO().insertMultiple(dataRetrieved.getTownCustomers());
            }

            if(dataRetrieved.getStatuses()!=null) {
                db.getStatusDAO().nukeTable();
                db.getStatusDAO().insertMultiple(dataRetrieved.getStatuses());
            }

            if(dataRetrieved.getSkuGroup()!=null) {
                db.getSdskuGroupDAO().nukeTable();
                db.getSdskuGroupDAO().insertMultiple(dataRetrieved.getSkuGroup());
            }

            if(dataRetrieved.getWorkWiths() != null){
                db.getWorkWithDAO().nukeTable();
                db.getWorkWithDAO().insertMultiple(dataRetrieved.getWorkWiths());
            }

            if(dataRetrieved.getDashboard() != null){
                db.getDashboardDAO().nukeTable();
                db.getDashboardDAO().insertMultiple(dataRetrieved.getDashboard());
            }
        }
        return "Data Saved";

    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }
*/
    public void performSync(final Activity context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);

        AppDatabase db = AppDatabase.getAppDatabase(context);

        List<QTVForm> qtvForms = db.getQTVFormDAO().getAll();
        SyncObject syncObject = new SyncObject();
        syncObject.setQtvForms(qtvForms);

        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);

        JSONObject response = null;

        HttpUtils.get("hssync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String status = "";
                String data = "";
                String codeReceived = "";
                String staffName = "";
                JSONObject params = new JSONObject();
                List<Integer> successfulIDs = new ArrayList<>();
                try {
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    staffName = response.get("staffName").toString();
                    params.put("message", message);
                    params.put("data", data);
                    params.put("staffName",staffName);
                    params.put("status",codeReceived);
                    for(int i=0;i<response.getJSONArray("successfulIDs").length();i++){
                        successfulIDs.add(response.getJSONArray("successfulIDs").getInt(i));
                    }
                }catch(Exception e){
                    Toast.makeText(context,"Something went wrong while sync",Toast.LENGTH_SHORT).show();
                }
                if(Codes.ALL_OK.equals(codeReceived)){
                    updateData(successfulIDs, context);
                    saveData(params, context);

                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                responseListener.responseAlert(Codes.SOMETHINGWENTWRONG);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                responseListener.responseAlert(Codes.TIMEOUT);
            }
        });
    }

    private void updateData(List<Integer> successfulIDs, Activity activity) {
        AppDatabase db = AppDatabase.getAppDatabase(activity);
        for(int id : successfulIDs){
            db.getQTVFormDAO().markQTVSuccessful(id);
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


