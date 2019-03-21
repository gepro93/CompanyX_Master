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

public class FacLaptopList extends AppCompatActivity {

    private Button btFacLaptopListBack;
    private ListView lwFacLaptopList;
    private Database db;
    private ArrayList<HashMap<String, String>> laptopBenefitList;
    private ListAdapter adapter;
    private int benefitId, statusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_laptop_list);
        init();
        createList();

        btFacLaptopListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopList.this,FacLaptopHandle.class));
                finish();
            }
        });
    }

    private void createList() {
        laptopBenefitList = db.viewActiveLaptopBenefit();
        adapter = new SimpleAdapter(FacLaptopList.this, laptopBenefitList, R.layout.laptop_benefit_edit,
                new String[]{"EMPLOYEE_NAME","LAPTOPTYPE","LAPTOP_IMEINUMBER","BENEFIT_ID","BENEFIT_STATUS"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfMobile,R.id.twMobileImeiNumber,benefitId,statusId});

        lwFacLaptopList.setAdapter(adapter);
    }

    private void init() {
        btFacLaptopListBack = findViewById(R.id.btFacLaptopListBack);
        lwFacLaptopList = findViewById(R.id.lwFacLaptopList);
        db = new Database(this);
    }
}
