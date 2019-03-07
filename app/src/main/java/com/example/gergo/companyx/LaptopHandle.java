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

public class LaptopHandle extends AppCompatActivity {

    private Button btLaptopCreate, btLaptopEdit, btLaptopList, btLaptopHandleBack;
    private Database db;
    private LoadScreen ls;
    private ArrayList<String> laptopType;
    private int selectedLaptopType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_handle);

        init();

        btLaptopCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLaptop();
            }
        });

        btLaptopEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopHandle.this, LaptopEdit.class));
                finish();
            }
        });

        btLaptopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopHandle.this, LaptopList.class));
                finish();
            }
        });

        btLaptopHandleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopHandle.this, LaptopMenu.class));
                finish();
            }
        });
    }

    private void createLaptop() {
        AlertDialog.Builder alert = new AlertDialog.Builder(LaptopHandle.this);

        alert.setMessage("Laptop felvétele:");
        alert.setTitle("Felvétel");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(LaptopHandle.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText laptopImeiNumber = new EditText(LaptopHandle.this);
        laptopImeiNumber.setHint("Laptop IMEI száma");
        laptopImeiNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        laptopImeiNumber.setGravity(Gravity.CENTER);
        laptopImeiNumber.setPadding(0, 0, 0, 60);
        layout.addView(laptopImeiNumber); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spLaptopType = new Spinner(LaptopHandle.this, Spinner.MODE_DROPDOWN);

        laptopType = db.modelOfLaptopList();
        laptopType.add(0,"Válassz gyártmányt!");

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(LaptopHandle.this, android.R.layout.simple_spinner_item, laptopType);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLaptopType.setAdapter(spinnerDataAdapter);
        layout.addView(spLaptopType); //Spinner hozzáadása layouthoz

        layout.setPadding(0, 30, 0, 30);
        alert.setView(layout);

        spLaptopType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Válassz gyártmányt!")) {
                    selectedLaptopType = 0;
                } else {
                    selectedLaptopType = adapterView.getSelectedItemPosition();
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
                String etImeiNumber = laptopImeiNumber.getText().toString();

                if (etImeiNumber.equals("") || selectedLaptopType == 0) {
                    Toast.makeText(LaptopHandle.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean laptopCheck = db.laptopCheck(etImeiNumber);
                    if (!laptopCheck) {
                        boolean createLaptop = db.insertLaptop(etImeiNumber,selectedLaptopType);
                        if (createLaptop) {
                            ls.progressDialog(LaptopHandle.this, "Laptop felvétele folyamatban...", "Létrehozás");
                        } else
                            Toast.makeText(LaptopHandle.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.show();
    }

    private void init() {
        btLaptopCreate = findViewById(R.id.btLaptopCreate);
        btLaptopEdit = findViewById(R.id.btLaptopEdit);
        btLaptopList = findViewById(R.id.btLaptopList);
        btLaptopHandleBack = findViewById(R.id.btLaptopHandleBack);
        db = new Database(this);
        ls = new LoadScreen();
    }
}
