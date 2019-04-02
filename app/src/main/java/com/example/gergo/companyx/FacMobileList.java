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
                Animatoo.animateSlideRight(FacMobileList.this);
                finish();
            }
        });
    }

    private void createList() {
        mobileBenefitList = db.viewActiveMobileBenefit();
        adapter = new SimpleAdapter(FacMobileList.this, mobileBenefitList, R.layout.mobile_benefit_edit,
                new String[]{"EMPLOYEE_NAME","MOBILTYPE","MOBIL_IMEINUMBER","BENEFIT_ID","BENEFIT_STATUS","USER_NAME"},
                new int[]{R.id.twNameOfEmp,R.id.twTypeOfMobile,R.id.twMobileImeiNumber,benefitId,statusId,R.id.twNameOfUser});

        lwFacMobileList.setAdapter(adapter);
    }

    private void init() {
        btFacMobileListBack = findViewById(R.id.btFacMobileListBack);
        lwFacMobileList = findViewById(R.id.lwFacMobileList);
        db = new Database(this);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacMobileList.this);

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
                startActivity(new Intent(FacMobileList.this, Login.class));
                Animatoo.animateFade(FacMobileList.this);
                finish();
            }
        });
        builder.show();
    }
}
