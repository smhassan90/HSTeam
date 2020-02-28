package com.greenstar.hsteam.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.greenstar.hsteam.R;
import com.greenstar.hsteam.db.AppDatabase;
import com.greenstar.hsteam.model.Area;
import com.greenstar.hsteam.model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QATForm extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llQuestion = null;
    LinearLayout llQuestionBank = null;
    TextView tvAreaHeading = null;
    AppDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qat_screen);
        populateQuestions();

    }

    private void populateQuestions() {
        LayoutInflater inflater = LayoutInflater.from(QATForm.this);
        db = AppDatabase.getAppDatabase(this);
        llQuestionBank = findViewById(R.id.llQuestionBank);

        List<Question> questions = new ArrayList<>();
        List<Area> areas = new ArrayList<>();

        questions = db.getQuestionsDAO().getActiveQuestions();
        areas = db.getAreaDAO().getActiveAreas();

        //Integer is the ID
        Map<Integer, RadioButton> mappingIDRadioButton = new HashMap<Integer, RadioButton>();
        for(Area area : areas){
            View rowQuestionHeading = inflater.inflate(R.layout.qat_area_heading, null);
            tvAreaHeading = rowQuestionHeading.findViewById(R.id.tvAreaHeading);
            tvAreaHeading.setText(area.getArea());
            for(Question question:questions){
                View rowQuestion = inflater.inflate(R.layout.qat_question_row, null);
                llQuestion = rowQuestion.findViewById(R.id.llQuestion);
            }
        }

        View rowQuestion = inflater.inflate(R.layout.qat_question_row, null);
        llQuestion = rowQuestion.findViewById(R.id.llQuestion);
        llQuestionBank.addView(llQuestion);
        llQuestionBank.addView(llQuestion);

    }

    @Override
    public void onClick(View v) {

    }
}
