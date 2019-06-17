package com.greenstar.hsteam.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);
        Button btnLogin = (Button)this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Logging in");
        dialog.setMessage("In Progress. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        EditText etEmpCode = findViewById(R.id.etEmpCode);
        String code = etEmpCode.getText().toString();
        if(code !=null && !"".equals(code)){
            loginHit(code);
        }else{
            Toast.makeText(this,"Employee code cannot be empty", Toast.LENGTH_LONG).show();
        }

    }

    private void loginHit(final String code){
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("uniqueId",HttpUtils.getUniqueId());
        dialog.show();
        HttpUtils.get("login", rp, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String  codeReceived = "";
                String data =null;
                String token="";
                try{
                    message = response.get("message").toString();
                    codeReceived = response.get("status").toString();
                    data =  response.get("data").toString();
                    token = response.get("token").toString();
                }catch(Exception e){

                }finally {
                    dialog.dismiss();
                }
                Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
                if(Codes.ALL_OK.equals(codeReceived)){
                    Toast.makeText(getApplicationContext(), "Done",Toast.LENGTH_SHORT).show();
//                    saveData(code,token);
//                    Intent myIntent = new Intent(Login.this, Menu.class);
//                    Login.this.startActivity(myIntent);
//                    finish();
                }
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void saveData(String code, String token){
        SharedPreferences.Editor editor = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE).edit();
        editor.putString("name", "Chandio");
        editor.putString("code", code);
        editor.putString("token", token);
        editor.putBoolean("isLoggedIn",true);
        editor.apply();
    }
}
