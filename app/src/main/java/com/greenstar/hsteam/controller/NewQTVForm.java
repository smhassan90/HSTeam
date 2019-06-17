package com.greenstar.hsteam.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.greenstar.hsteam.R;

public class NewQTVForm extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    /*
    Matrix Common elements initialization STARTS
     */
    final static int COLUMN_LENGTH=6;
    final static int ROW_LENGTH=7;

    private static final int[] tvTotalRowsIds = {R.id.total1, R.id.total2, R.id.total3, R.id.total4, R.id.total5,
            R.id.total6, R.id.total7, R.id.total8};
    private static final int[] tvTotalColumnsIds = {R.id.tv81, R.id.tv82, R.id.tv83, R.id.tv84, R.id.tv85, R.id.tv86};
    /*
    Matrix Common elements initialization ENDS
     */
    /*
    Matrix1 - Overall Method Mix Disaggregated by Age
    Matrix2 - Post- Partum Family Planning (PPIUD Clients)
    Matrix3 - Post PAC Family Planning (Both Miso and MVA Clients)
    Matrix4 - New Users Method Mix
     */

    // Matrix1 - Overall Method Mix Disaggregated by Age
    //Declaration Starts
    private static final int[][] btnMatrix1Ids = {
            {R.id.btn111, R.id.btn112, R.id.btn113, R.id.btn114, R.id.btn115, R.id.btn116},
            {R.id.btn121, R.id.btn122, R.id.btn123, R.id.btn124, R.id.btn125, R.id.btn126},
            {R.id.btn131, R.id.btn132, R.id.btn133, R.id.btn134, R.id.btn135, R.id.btn136},
            {R.id.btn141, R.id.btn142, R.id.btn143, R.id.btn144, R.id.btn145, R.id.btn146},
            {R.id.btn151, R.id.btn152, R.id.btn153, R.id.btn154, R.id.btn155, R.id.btn156},
            {R.id.btn161, R.id.btn162, R.id.btn163, R.id.btn164, R.id.btn165, R.id.btn166},
            {R.id.btn171, R.id.btn172, R.id.btn173, R.id.btn174, R.id.btn175, R.id.btn176}
    };

    Button btnMatrix1[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow1 = new TextView[ROW_LENGTH+1];
    TextView[] totalColumn1 = new TextView[COLUMN_LENGTH];

    View glMatrix1;

    // Matrix1 - Overall Method Mix Disaggregated by Age
    //Declaration ends

    // Matrix2 - Post- Partum Family Planning (PPIUD Clients)
    //Declaration Starts

    private static final int[][] btnMatrix2Ids = {
            {R.id.btn211, R.id.btn212, R.id.btn213, R.id.btn214, R.id.btn215, R.id.btn216},
            {R.id.btn221, R.id.btn222, R.id.btn223, R.id.btn224, R.id.btn225, R.id.btn226},
            {R.id.btn231, R.id.btn232, R.id.btn233, R.id.btn234, R.id.btn235, R.id.btn236},
            {R.id.btn241, R.id.btn242, R.id.btn243, R.id.btn244, R.id.btn245, R.id.btn246},
            {R.id.btn251, R.id.btn252, R.id.btn253, R.id.btn254, R.id.btn255, R.id.btn256},
            {R.id.btn261, R.id.btn262, R.id.btn263, R.id.btn264, R.id.btn265, R.id.btn266},
            {R.id.btn271, R.id.btn272, R.id.btn273, R.id.btn274, R.id.btn275, R.id.btn276}
    };

    Button btnMatrix2[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow2 = new TextView[ROW_LENGTH+1];
    TextView[] totalColumn2 = new TextView[COLUMN_LENGTH];

    View glMatrix2;
    //Matrix 2 - Ends

    // Matrix3 - Post PAC Family Planning (Both Miso and MVA Clients)
    //Declaration Starts
    private static final int[][] btnMatrix3Ids = {
            {R.id.btn311, R.id.btn312, R.id.btn313, R.id.btn314, R.id.btn315, R.id.btn316},
            {R.id.btn321, R.id.btn322, R.id.btn323, R.id.btn324, R.id.btn325, R.id.btn326},
            {R.id.btn331, R.id.btn332, R.id.btn333, R.id.btn334, R.id.btn335, R.id.btn336},
            {R.id.btn341, R.id.btn342, R.id.btn343, R.id.btn344, R.id.btn345, R.id.btn346},
            {R.id.btn351, R.id.btn352, R.id.btn353, R.id.btn354, R.id.btn355, R.id.btn356},
            {R.id.btn361, R.id.btn362, R.id.btn363, R.id.btn364, R.id.btn365, R.id.btn366},
            {R.id.btn371, R.id.btn372, R.id.btn373, R.id.btn374, R.id.btn375, R.id.btn376}
    };

    Button btnMatrix3[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow3 = new TextView[ROW_LENGTH+1];
    TextView[] totalColumn3 = new TextView[COLUMN_LENGTH];

    View glMatrix3;

    // Matrix3 - Post PAC Family Planning (Both Miso and MVA Clients)
    //Declaration ends

    // Matrix4 - New Users Method Mix
    //Declaration Starts
    private static final int[][] btnMatrix4Ids = {
            {R.id.btn411, R.id.btn412, R.id.btn413, R.id.btn414, R.id.btn415, R.id.btn416},
            {R.id.btn421, R.id.btn422, R.id.btn423, R.id.btn424, R.id.btn425, R.id.btn426},
            {R.id.btn431, R.id.btn432, R.id.btn433, R.id.btn434, R.id.btn435, R.id.btn436},
            {R.id.btn441, R.id.btn442, R.id.btn443, R.id.btn444, R.id.btn445, R.id.btn446},
            {R.id.btn451, R.id.btn452, R.id.btn453, R.id.btn454, R.id.btn455, R.id.btn456},
            {R.id.btn461, R.id.btn462, R.id.btn463, R.id.btn464, R.id.btn465, R.id.btn466},
            {R.id.btn471, R.id.btn472, R.id.btn473, R.id.btn474, R.id.btn475, R.id.btn476}
    };

    Button btnMatrix4[][] = new Button[ROW_LENGTH][COLUMN_LENGTH];

    TextView[] totalRow4 = new TextView[ROW_LENGTH+1];
    TextView[] totalColumn4 = new TextView[COLUMN_LENGTH];

    View glMatrix4;

    // Matrix4 - New Users Method Mix
    //Declaration ends


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_qtv_screen);

        glMatrix1 = findViewById(R.id.glMatrix1);
        glMatrix2 = findViewById(R.id.glMatrix2);
        glMatrix3 = findViewById(R.id.glMatrix3);
        glMatrix4 = findViewById(R.id.glMatrix4);

        // Total FP clients initialize elements in a matrix
        initializeMatrix1Elements();

    }

    /*
        Total FP clients initialize elements in a matrix1
     */
    private void initializeMatrix1Elements() {
        for(int i=0; i<ROW_LENGTH;i++){
            for(int j=0; j<COLUMN_LENGTH;j++){
                //Matrix1 initialization
                btnMatrix1[i][j] = glMatrix1.findViewById(btnMatrix1Ids[i][j]);
                btnMatrix1[i][j].setOnLongClickListener(this);
                btnMatrix1[i][j].setOnClickListener(this);

                btnMatrix2[i][j] = glMatrix2.findViewById(btnMatrix2Ids[i][j]);
                btnMatrix2[i][j].setOnLongClickListener(this);
                btnMatrix2[i][j].setOnClickListener(this);

                btnMatrix3[i][j] = glMatrix3.findViewById(btnMatrix3Ids[i][j]);
                btnMatrix3[i][j].setOnLongClickListener(this);
                btnMatrix3[i][j].setOnClickListener(this);

                btnMatrix4[i][j] = glMatrix4.findViewById(btnMatrix4Ids[i][j]);
                btnMatrix4[i][j].setOnLongClickListener(this);
                btnMatrix4[i][j].setOnClickListener(this);
            }
        }

        for(int i = 0; i<=ROW_LENGTH;i++){
            totalRow1[i] = glMatrix1.findViewById(tvTotalRowsIds[i]);
            totalRow2[i] = glMatrix2.findViewById(tvTotalRowsIds[i]);
            totalRow3[i] = glMatrix3.findViewById(tvTotalRowsIds[i]);
            totalRow4[i] = glMatrix4.findViewById(tvTotalRowsIds[i]);
        }

        for(int i = 0; i<COLUMN_LENGTH;i++){
            totalColumn1[i] = glMatrix1.findViewById(tvTotalColumnsIds[i]);
            totalColumn2[i] = glMatrix2.findViewById(tvTotalColumnsIds[i]);
            totalColumn3[i] = glMatrix3.findViewById(tvTotalColumnsIds[i]);
            totalColumn4[i] = glMatrix4.findViewById(tvTotalColumnsIds[i]);
        }
    }

    @Override
    public void onClick(View v) {
        handleMatrixClick(v.getId());
    }

    /*
        It will handle all clicks on the matrix and calculate the total
     */
    private void handleMatrixClick(int id) {
        int count=0;

        for(int i=0; i<ROW_LENGTH;i++){
            for(int j=0; j<COLUMN_LENGTH;j++){
                if(id == btnMatrix1[i][j].getId()){
                    count = Integer.valueOf(btnMatrix1[i][j].getText().toString())+1;
                    btnMatrix1[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix1();
                }else if(id == btnMatrix2[i][j].getId()){
                    count = Integer.valueOf(btnMatrix2[i][j].getText().toString())+1;
                    btnMatrix2[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix2();
                }else if(id == btnMatrix3[i][j].getId()){
                    count = Integer.valueOf(btnMatrix3[i][j].getText().toString())+1;
                    btnMatrix3[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix3();
                }else if(id == btnMatrix4[i][j].getId()){
                    count = Integer.valueOf(btnMatrix4[i][j].getText().toString())+1;
                    btnMatrix4[i][j].setText(String.valueOf(count));
                    // Calculate Total.
                    calculateTotalMatrix4();
                }
            }
        }

    }

    /*
        Decrement cell value
    */
    private void decrementValue(int id){

            int count=0;
            int currentValue=0;

            for(int i=0; i<ROW_LENGTH;i++){
                for(int j=0; j<COLUMN_LENGTH;j++){
                    if(id == btnMatrix1[i][j].getId()){
                        currentValue = Integer.valueOf(btnMatrix1[i][j].getText().toString());
                        if(currentValue>0) {
                            count = currentValue-1;
                            btnMatrix1[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix1();
                            break;
                        }
                    }else if(id == btnMatrix2[i][j].getId()){
                        currentValue = Integer.valueOf(btnMatrix2[i][j].getText().toString());
                        if(currentValue>0) {
                            count = currentValue-1;
                            btnMatrix2[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix2();
                            break;
                        }
                    }else if(id == btnMatrix3[i][j].getId()){
                        currentValue = Integer.valueOf(btnMatrix3[i][j].getText().toString());
                        if(currentValue>0) {
                            count = currentValue-1;
                            btnMatrix3[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix3();
                            break;
                        }
                    }else if(id == btnMatrix4[i][j].getId()){
                        currentValue = Integer.valueOf(btnMatrix4[i][j].getText().toString());
                        if(currentValue>0) {
                            count = currentValue-1;
                            btnMatrix4[i][j].setText(String.valueOf(count));

                            // Calculate Total.
                            calculateTotalMatrix4();
                            break;
                        }
                    }
                }
            }

    }


    /*
        Calculate all totals of Matrix 1
    */
    private void calculateTotalMatrix1() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix1[j][i].getText().toString());
            }
            totalColumn1[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix1[i][j].getText().toString());
            }
            totalRow1[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn1[i].getText().toString());
        }

        totalRow1[ROW_LENGTH].setText(String.valueOf(count));
    }


    /*
        Calculate all totals of Matrix 2
    */
    private void calculateTotalMatrix2() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix2[j][i].getText().toString());
            }
            totalColumn2[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix2[i][j].getText().toString());
            }
            totalRow2[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn2[i].getText().toString());
        }

        totalRow2[ROW_LENGTH].setText(String.valueOf(count));
    }

    /*
        Calculate all totals of Matrix 3
    */
    private void calculateTotalMatrix3() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix3[j][i].getText().toString());
            }
            totalColumn3[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix3[i][j].getText().toString());
            }
            totalRow3[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn3[i].getText().toString());
        }

        totalRow3[ROW_LENGTH].setText(String.valueOf(count));
    }

    /*
        Calculate all totals of Matrix 4
    */
    private void calculateTotalMatrix4() {
        int count = 0;
        //Sum all columns
        for(int i =0 ; i<COLUMN_LENGTH;i++){
            count = 0;
            for(int j=0; j < ROW_LENGTH; j++){
                count += Integer.valueOf(btnMatrix4[j][i].getText().toString());
            }
            totalColumn4[i].setText(String.valueOf(count));
        }

        //Sum all rows
        for(int i =0 ; i<ROW_LENGTH;i++){
            count = 0;
            for(int j=0; j < COLUMN_LENGTH; j++){
                count += Integer.valueOf(btnMatrix4[i][j].getText().toString());
            }
            totalRow4[i].setText(String.valueOf(count));
        }

        count=0;
        //Sum all sums in last row
        for(int i = 0; i<COLUMN_LENGTH;i++){
            count += Integer.valueOf(totalColumn4[i].getText().toString());
        }

        totalRow4[ROW_LENGTH].setText(String.valueOf(count));
    }

    @Override
    public boolean onLongClick(View v) {
        decrementValue(v.getId());
        return true;
    }
}
