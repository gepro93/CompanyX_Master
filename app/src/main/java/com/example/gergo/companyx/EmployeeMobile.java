package com.example.gergo.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;

public class EmployeeMobile extends AppCompatActivity {

    private Button btEmployeeMobileBack;
    private TextView twMobileType, twImei;
    private Database db;
    private String userName, mobileType, mobileImei;
    private ArrayList<String> mobileDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_mobile);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        setTextViewText();

        btEmployeeMobileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeMobile.this, EmployeeMenu.class));
                Animatoo.animateSlideRight(EmployeeMobile.this);
                finish();
            }
        });
    }

    private void setTextViewText(){
        mobileDetails = db.viewMobileBenefitByUser(userName);
        if (mobileDetails.size()>0){
            mobileType = mobileDetails.get(0);
            mobileImei = mobileDetails.get(1);
            twMobileType.setText(mobileType);
            twImei.setText(mobileImei);
        }else Toast.makeText(this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        btEmployeeMobileBack = findViewById(R.id.btEmployeeMobileBack);
        twMobileType = findViewById(R.id.twMobileType);
        twImei = findViewById(R.id.twImei);
        db = new Database(this);
    }
}
