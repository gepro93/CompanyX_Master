package com.example.gergo.companyx;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CarHandle extends AppCompatActivity {

    private Button btCarCreate, btCarEdit, btCarList, btCarHandleBack;
    private Database db;
    private LoadScreen ls;
    private Calendar c;
    private DatePickerDialog dpd;
    private ArrayList<String> carType;
    private int selectedCarType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_handle);
        init();

        btCarCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCar();
            }
        });

        btCarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarHandle.this, CarEdit.class));
                finish();
            }
        });

        btCarHandleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarHandle.this, CarMenu.class));
                finish();
            }
        });

        btCarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarHandle.this, CarList.class));
                finish();
            }
        });

    }

    private void init() {
        btCarCreate = findViewById(R.id.btCarCreate);
        btCarEdit = findViewById(R.id.btCarEdit);
        btCarList = findViewById(R.id.btCarList);
        btCarHandleBack = findViewById(R.id.btCarHandleBack);
        db = new Database(this);
        ls = new LoadScreen();
    }

    private void addCar() {
        AlertDialog.Builder alert = new AlertDialog.Builder(CarHandle.this);

        alert.setMessage("Autó felvétele:");
        alert.setTitle("Felvétel");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(CarHandle.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText licenseNumber = new EditText(CarHandle.this);
        licenseNumber.setHint("Rendszám");
        licenseNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        licenseNumber.setGravity(Gravity.CENTER);
        licenseNumber.setPadding(0, 0, 0, 60);
        layout.addView(licenseNumber); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText vinNumber = new EditText(CarHandle.this);
        vinNumber.setHint("Alvázszám");
        vinNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        vinNumber.setGravity(Gravity.CENTER);
        vinNumber.setPadding(0, 60, 0, 60);
        layout.addView(vinNumber); //Edit Text hozzáadása layouthoz

        //Edit Text létrehozása
        final EditText motDate = new EditText(CarHandle.this);
        motDate.setHint("Műszaki érvényessége");
        motDate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        motDate.setGravity(Gravity.CENTER);
        motDate.setPadding(0, 60, 0, 60);

        motDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(CarHandle.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        if(mMonth < 10 && mDay < 10){
                            motDate.setText(mYear + "-0" + (mMonth+1) + "-0" + mDay);
                        }else if (mMonth < 10){
                            motDate.setText(mYear + "-0" + (mMonth+1) + "-" + mDay);
                        }else if(mDay < 10){
                            motDate.setText(mYear + "-" + (mMonth+1) + "-0" + mDay);
                        }else motDate.setText(mYear + "-" + (mMonth+1) + "-" + mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        layout.addView(motDate); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spCarType = new Spinner(CarHandle.this, Spinner.MODE_DROPDOWN);

        carType = db.modelOfCarList();
        carType.add(0,"Válassz gyártmányt!");

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(CarHandle.this, android.R.layout.simple_spinner_item, carType);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCarType.setAdapter(spinnerDataAdapter);
        layout.addView(spCarType); //Spinner hozzáadása layouthoz

        layout.setPadding(0, 30, 0, 30);
        alert.setView(layout);

        spCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Válassz gyártmányt!")) {
                    selectedCarType = 0;
                } else {
                    selectedCarType = adapterView.getSelectedItemPosition();
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
               String etLicenseNumber = licenseNumber.getText().toString();
               String etVinNumber = vinNumber.getText().toString();

               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
               String etMotDate = motDate.getText().toString();

                Date formattedMotDate = null;
                try {
                    formattedMotDate = formatter.parse(etMotDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (etLicenseNumber.equals("") || etVinNumber.equals("") || etMotDate.equals("") || selectedCarType == 0) {
                    Toast.makeText(CarHandle.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean carCheck = db.carCheck(etLicenseNumber,etVinNumber);
                    if (!carCheck) {
                        boolean createCar = db.insertCar(etLicenseNumber,etVinNumber, formattedMotDate,selectedCarType);
                        if (createCar) {
                            ls.progressDialog(CarHandle.this, "Autó felvétele folyamatban...", "Létrehozás");
                        } else
                            Toast.makeText(CarHandle.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.show();
    }

}