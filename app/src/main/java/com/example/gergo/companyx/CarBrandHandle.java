package com.example.gergo.companyx;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CarBrandHandle extends AppCompatActivity {

    private Button btCarBrandCreate, btCarBrandEdit, btCarBrandList, btCarBrandHandleBack;
    private Database db;
    private LoadScreen ls;
    private ArrayList<String> gradeCategory;
    private int selectedGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand_handle);
        init();

        btCarBrandCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarBrand();
            }
        });

        btCarBrandEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarBrandHandle.this, CarBrandEdit.class));
                finish();
            }
        });

        btCarBrandList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarBrandHandle.this, CarBrandList.class));
                finish();
            }
        });

        btCarBrandHandleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarBrandHandle.this,CarMenu.class));
                finish();
            }
        });

    }

    private void addCarBrand() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CarBrandHandle.this);

        alert.setMessage("Márka felvétele:");
        alert.setTitle("Felvétel");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(CarBrandHandle.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText brandName = new EditText(CarBrandHandle.this);
        brandName.setHint("Márka");
        brandName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        brandName.setGravity(Gravity.CENTER);
        brandName.setPadding(0, 0, 0, 60);
        layout.addView(brandName); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText typeName = new EditText(CarBrandHandle.this);
        typeName.setHint("Típus");
        typeName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        typeName.setGravity(Gravity.CENTER);
        typeName.setPadding(0, 60, 0, 60);
        layout.addView(typeName); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spGrade = new Spinner(CarBrandHandle.this, Spinner.MODE_DROPDOWN);

        gradeCategory = db.gradeList();
        gradeCategory.add(0,"Válassz grade kategóriát!");

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(CarBrandHandle.this, android.R.layout.simple_spinner_item, gradeCategory);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrade.setAdapter(spinnerDataAdapter);
        layout.addView(spGrade); //Spinner hozzáadása layouthoz

        layout.setPadding(0, 30, 0, 30);
        alert.setView(layout);

        spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Válassz gyártmányt!")) {
                    selectedGrade = 0;
                } else {
                    selectedGrade = adapterView.getSelectedItemPosition();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        alert.setPositiveButton("Mégsem", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.setNegativeButton("Mentés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String etBrandName = brandName.getText().toString();
                String etTypeName = typeName.getText().toString();

                if (etBrandName.equals("") || etTypeName.equals("")) {
                    Toast.makeText(CarBrandHandle.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean brandCheck = db.carBrandCheck(etBrandName,etTypeName);
                    if (!brandCheck) {
                        boolean createCarBrand = db.insertModelOfCar(etBrandName,etTypeName,selectedGrade);
                        if (createCarBrand) {
                            ls.progressDialog(CarBrandHandle.this, "Márka felvétele folyamatban...", "Létrehozás");
                        } else
                            Toast.makeText(CarBrandHandle.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.show();
    }

    private void init() {
        btCarBrandCreate = findViewById(R.id.btCarBrandCreate);
        btCarBrandEdit = findViewById(R.id.btCarBrandEdit);
        btCarBrandList = findViewById(R.id.btCarBrandList);
        btCarBrandHandleBack = findViewById(R.id.btCarBrandHandleBack);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
