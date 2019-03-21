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

public class FacMobileList extends AppCompatActivity {

    private Button btFacMobileListBack;
    private ListView lwFacMobileList;
    private Database db;
    private ArrayList<HashMap<String, String>> mobileBenefitList;
    private ListAdapter adapter;
    private int benefitId, statusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_mobile_list);
        init();
        createList();

        btFacMobileListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileList.this,FacMobileHandle.class));
                finish();
            }
        });
    }

    private void createList() {
        mobileBenefitList = db.viewActiveMobileBenefit();
        adapter = new SimpleAdapter(FacMobileList.this, mobileBenefitList, R.layout.mobile_benefit_edit,
                new String[]{"EMPLOYEE_NAME","MOBILTYPE","MOBIL_IMEINUMBER","BENEFIT_ID","BENEFIT_STATUS"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfMobile,R.id.twMobileImeiNumber,benefitId,statusId});

        lwFacMobileList.setAdapter(adapter);
    }

    private void init() {
        btFacMobileListBack = findViewById(R.id.btFacMobileListBack);
        lwFacMobileList = findViewById(R.id.lwFacMobileList);
        db = new Database(this);
    }
}
