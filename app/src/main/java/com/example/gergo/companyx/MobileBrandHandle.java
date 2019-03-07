package com.example.gergo.companyx;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MobileBrandHandle extends AppCompatActivity {

    private Button btMobileBrandCreate, btMobileBrandEdit, btMobileBrandList, btMobileBrandHandleBack;
    private Database db;
    private LoadScreen ls;
    private ArrayList<String> gradeCategory;
    private int selectedGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_brand_handle);
        init();

        btMobileBrandCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMobileBrand();
            }
        });

        btMobileBrandEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileBrandHandle.this, MobileBrandEdit.class));
                finish();
            }
        });

        btMobileBrandList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileBrandHandle.this, MobileBrandList.class));
                finish();
            }
        });

        btMobileBrandHandleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileBrandHandle.this, MobileMenu.class));
                finish();
            }
        });

    }

    private void createMobileBrand() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MobileBrandHandle.this);

        alert.setMessage("Márka felvétele:");
        alert.setTitle("Felvétel");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(MobileBrandHandle.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText brandName = new EditText(MobileBrandHandle.this);
        brandName.setHint("Márka");
        brandName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        brandName.setGravity(Gravity.CENTER);
        brandName.setPadding(0, 0, 0, 60);
        layout.addView(brandName); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText typeName = new EditText(MobileBrandHandle.this);
        typeName.setHint("Típus");
        typeName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        typeName.setGravity(Gravity.CENTER);
        typeName.setPadding(0, 60, 0, 60);
        layout.addView(typeName); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spGrade = new Spinner(MobileBrandHandle.this, Spinner.MODE_DROPDOWN);

        gradeCategory = db.gradeList();
        gradeCategory.add(0,"Válassz grade kategóriát!");

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(MobileBrandHandle.this, android.R.layout.simple_spinner_item, gradeCategory);
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
                    Toast.makeText(MobileBrandHandle.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean brandCheck = db.mobileBrandCheck(etBrandName,etTypeName);
                    if (!brandCheck) {
                        boolean createMobileBrand = db.insertModelOfMobile(etBrandName,etTypeName,selectedGrade);
                        if (createMobileBrand) {
                            ls.progressDialog(MobileBrandHandle.this, "Márka felvétele folyamatban...", "Létrehozás");
                        } else
                            Toast.makeText(MobileBrandHandle.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.show();
    }

    private void init() {
        btMobileBrandCreate = findViewById(R.id.btMobileBrandCreate);
        btMobileBrandEdit = findViewById(R.id.btMobileBrandEdit);
        btMobileBrandList = findViewById(R.id.btMobileBrandList);
        btMobileBrandHandleBack = findViewById(R.id.btMobileBrandHandleBack);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
