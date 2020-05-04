package com.greenstar.hsteam.controller.qat;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.adapters.ProviderAdapter;
import com.greenstar.hsteam.controller.Codes;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.Providers;
import com.greenstar.hsteam.model.QATTCForm;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TCForm extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = null;
    SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

    Button btnSubmit;
    EditText etDateOFAssessment;
    TextView tvQAMCode, tvQAMName, tvSupervisorCode, tvSupervisorName, tvRegion;

    SearchableSpinner spProviderCodeName;
    ProviderAdapter providerAdapter;

    List<Providers> providers = new ArrayList<>();

    AppDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc_form);

        db = AppDatabase.getAppDatabase(this);

        initializeForm();
        popoulateForm();
    }

    private void popoulateForm(){
        SharedPreferences prefs = this.getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String region = prefs.getString("region", "");
        String AMName = prefs.getString("AMName", "");
        String AMCode = prefs.getString("AMCode", "");
        String name = prefs.getString("name", "");
        String staffCode = prefs.getString("staffCode", "");

        tvSupervisorName.setText(AMName.toString());
        tvSupervisorCode.setText(AMCode);
        tvQAMName.setText(name.toString());
        tvQAMCode.setText(staffCode);
        tvRegion.setText(region.toString());

        //Providers spinner populate
        providers = db.getProvidersDAO().getActiveProviders();

        Providers first = new Providers();
        first.setCode("0");
        first.setName("Please Select");
        providers.add(0, first);
        providerAdapter = new ProviderAdapter(this, R.layout.provider_town_list, R.id.tvProviderNamess, providers);
        spProviderCodeName.setAdapter(providerAdapter);
    }

    private void initializeForm(){

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        etDateOFAssessment = findViewById(R.id.etDateOFAssessment);
        tvQAMCode = findViewById(R.id.tvQAMCode);
        tvQAMName = findViewById(R.id.tvQAMName);
        tvSupervisorCode = findViewById(R.id.tvSupervisorCode);
        tvSupervisorName = findViewById(R.id.tvSupervisorName);
        tvRegion = findViewById(R.id.tvRegion);
        spProviderCodeName = findViewById(R.id.spProviderCodeName);

        etDateOFAssessment.setOnClickListener(this);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateAssessmentDate();
            }

        };
    }

    private void updateAssessmentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(Codes.myFormat);

        etDateOFAssessment.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSubmit){
            if(isValid()){
                QATTCForm qattcForm = new QATTCForm();
                qattcForm.setDateOfAssessment(etDateOFAssessment.getText().toString());
                qattcForm.setMobileDate(sdf.format(myCalendar.getTime()));
                qattcForm.setQAMCode(tvQAMName.getText().toString());
                qattcForm.setRegion(tvRegion.getText().toString());
                qattcForm.setSupervisorCode(tvSupervisorCode.getText().toString());
                qattcForm.setSupervisorName(tvSupervisorName.getText().toString());
                Providers provider = (Providers) spProviderCodeName.getSelectedItem();
                qattcForm.setProviderCode(provider.getCode());
                qattcForm.setProviderName(provider.getName());

            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    private boolean isValid(){
        boolean isValid=true;

        return isValid;
    }
}
