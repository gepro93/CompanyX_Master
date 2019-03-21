package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class FacCarList extends AppCompatActivity {

    private Button btFacCarListBack;
    private ListView lwFacCarList;
    private Database db;
    private ArrayList<HashMap<String, String>> carBenefitList;
    private ListAdapter adapter;
    private int benefitId, statusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_car_list);
        init();
        createList();

        btFacCarListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarList.this,FacCarHandle.class));
                finish();
            }
        });
    }

    private void createList() {
        carBenefitList = db.viewActiveCarBenefit();
        adapter = new SimpleAdapter(FacCarList.this, carBenefitList, R.layout.car_benefit_edit,
                new String[]{"EMPLOYEE_NAME","CARTYPE","CAR_LICENSENUMBER","BENEFIT_ID","BENEFIT_STATUS"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfCar,R.id.twLicNumber,benefitId,statusId});

        lwFacCarList.setAdapter(adapter);
    }

    private void init() {
        btFacCarListBack = findViewById(R.id.btFacCarListBack);
        lwFacCarList = findViewById(R.id.lwFacCarList);
        db = new Database(this);
    }
}
