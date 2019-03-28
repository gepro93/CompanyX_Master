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

public class EmployeeLaptop extends AppCompatActivity {

    private Button btEmployeeLaptopBack;
    private TextView twLaptopType, twImei;
    private Database db;
    private String userName, laptopType, laptopImei;
    private ArrayList<String> laptopDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_laptop);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        setTextViewText();

        btEmployeeLaptopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeLaptop.this, EmployeeMenu.class));
                Animatoo.animateSlideRight(EmployeeLaptop.this);
                finish();
            }
        });
    }

    private void setTextViewText(){
        laptopDetails = db.viewLaptopBenefitByUser(userName);
        if (laptopDetails.size()>0){
            laptopType = laptopDetails.get(0);
            laptopImei = laptopDetails.get(1);
            twLaptopType.setText(laptopType);
            twImei.setText(laptopImei);
        }else Toast.makeText(this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        btEmployeeLaptopBack = findViewById(R.id.btEmployeeLaptopBack);
        twLaptopType = findViewById(R.id.twLaptopType);
        twImei = findViewById(R.id.twImei);
        db = new Database(this);
    }
}
