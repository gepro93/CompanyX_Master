package com.example.gergo.companyx;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;

public class EmployeeCarMenu extends AppCompatActivity {

    private TextView twEmpCarType, twEmpCarLic, twEmpCarVin, twEmpCarMot;
    private Button btAddTrip, btTripList, btEmployeeCarMenuBack;
    private Database db;
    private ArrayList<String> carDetails;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_car_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        carDetails = db.viewCarBenefitByUser(userName);
        twEmpCarType.setText(carDetails.get(0));
        twEmpCarLic.setText(carDetails.get(1));
        twEmpCarVin.setText(carDetails.get(2));
        twEmpCarMot.setText(carDetails.get(3));

        btAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeCarMenu.this, EmployeeTrip.class));
                Animatoo.animateSlideLeft(EmployeeCarMenu.this);
                finish();
            }
        });

        btTripList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeCarMenu.this, EmployeeTripList.class));
                Animatoo.animateSlideLeft(EmployeeCarMenu.this);
                finish();
            }
        });

        btEmployeeCarMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeCarMenu.this, EmployeeMenu.class));
                Animatoo.animateSlideLeft(EmployeeCarMenu.this);
                finish();
            }
        });

    }

    private void init() {
        twEmpCarType = findViewById(R.id.twEmpCarType);
        twEmpCarLic = findViewById(R.id.twEmpCarLic);
        twEmpCarVin = findViewById(R.id.twEmpCarVin);
        twEmpCarMot = findViewById(R.id.twEmpCarMot);
        btAddTrip = findViewById(R.id.btAddTrip);
        btTripList = findViewById(R.id.btTripList);
        btEmployeeCarMenuBack = findViewById(R.id.btEmployeeCarMenuBack);
        db = new Database(this);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeCarMenu.this);

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
                startActivity(new Intent(EmployeeCarMenu.this, Login.class));
                Animatoo.animateFade(EmployeeCarMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
