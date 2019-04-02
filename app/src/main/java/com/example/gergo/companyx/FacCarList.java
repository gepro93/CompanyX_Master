package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

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
                Animatoo.animateSlideRight(FacCarList.this);
                finish();
            }
        });
    }

    private void createList() {
        carBenefitList = db.viewActiveCarBenefit();
        adapter = new SimpleAdapter(FacCarList.this, carBenefitList, R.layout.car_benefit_edit,
                new String[]{"EMPLOYEE_NAME","CARTYPE","CAR_LICENSENUMBER","BENEFIT_ID","BENEFIT_STATUS","USER_NAME"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfCar,R.id.twLicNumber,benefitId,statusId,R.id.twNameOfUser});

        lwFacCarList.setAdapter(adapter);
    }

    private void init() {
        btFacCarListBack = findViewById(R.id.btFacCarListBack);
        lwFacCarList = findViewById(R.id.lwFacCarList);
        db = new Database(this);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacCarList.this);

        builder.setCancelable(true);
        builder.setTitle("Kijelentkezés");
        builder.setMessage("Valóban kijelentkezel?");
        builder.setIcon(R.drawable.ic_dialog_error);

        builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(FacCarList.this, Login.class));
                Animatoo.animateFade(FacCarList.this);
                finish();
            }
        });
        builder.show();
    }
}
