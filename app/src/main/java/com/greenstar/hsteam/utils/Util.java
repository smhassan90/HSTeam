package com.greenstar.hsteam.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.greenstar.hsteam.controller.Codes;
import com.greenstar.hsteam.dal.HSData;
import com.greenstar.hsteam.db.AppDatabase;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class Util {
   // static WebserviceResponse responseListener;

    public static void saveData(JSONObject params, Activity activity){
        AppDatabase db =null;
        db = AppDatabase.getAppDatabase(activity);
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

            db.getProvidersDAO().nukeTable();
            db.getProvidersDAO().insertMultiple(dataObj.getProviders());
            db.close();
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

    public static void performSync(final Context context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);
        AppDatabase db = AppDatabase.getAppDatabase(context);
        List<ProductOrderRelationship> orderProduct = db.getProductOderDAO().loadProductOrder();
        List<UnapprovedSDCustomer> unapprovedSDCustomers = db.getUnapprovedSDCustomerDAO().getAll();
        List<LeaveEntry> leaveEntries = db.getLeaveEntryDAO().getAll();
        SyncObject syncObject = new SyncObject();
        syncObject.setOrderProduct(orderProduct);
        syncObject.setUnapprovedSDCustomers(unapprovedSDCustomers);
        syncObject.setLeaveEntries(leaveEntries);
        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);

        JSONObject response = null;
        HttpUtils.get("sync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String status = "";
                String data = "";
                try {
                    status = response.get("status").toString();
                    data = response.get("data").toString();
                }catch(Exception e){

                }
                if(Codes.ALL_OK.equals(status)){
                    saveDataFromJSON(data, context);

                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                int i =9;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public WebserviceResponse getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(WebserviceResponse responseListener) {
        this.responseListener = responseListener;
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

}


