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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;


public class MobileHandle extends AppCompatActivity {

    private Button btMobileCreate, btMobileEdit, btMobileList, btMobileHandleBack;
    private Database db;
    private LoadScreen ls;
    private ArrayList<String> mobileType;
    private int selectedMobileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_handle);
        init();

        btMobileCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMobile();
            }
        });

        btMobileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileHandle.this, MobileEdit.class));
                Animatoo.animateSlideLeft(MobileHandle.this);
                finish();
            }
        });

        btMobileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileHandle.this, MobileList.class));
                Animatoo.animateSlideLeft(MobileHandle.this);
                finish();
            }
        });

        btMobileHandleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileHandle.this, MobileMenu.class));
                Animatoo.animateSlideRight(MobileHandle.this);
                finish();
            }
        });

    }

    private void createMobile() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MobileHandle.this);

        alert.setMessage("Mobil felvétele:");
        alert.setTitle("Felvétel");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(MobileHandle.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText mobileImeiNumber = new EditText(MobileHandle.this);
        mobileImeiNumber.setHint("Mobil IMEI száma");
        mobileImeiNumber.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        mobileImeiNumber.setGravity(Gravity.CENTER);
        mobileImeiNumber.setPadding(0, 0, 0, 60);
        layout.addView(mobileImeiNumber); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner spMobileType = new Spinner(MobileHandle.this, Spinner.MODE_DROPDOWN);

        mobileType = db.modelOfMobileList();
        mobileType.add(0,"Válassz gyártmányt!");

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(MobileHandle.this, android.R.layout.simple_spinner_item, mobileType);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMobileType.setAdapter(spinnerDataAdapter);
        spMobileType.setBackgroundResource(R.color.colorWhite);
        layout.addView(spMobileType); //Spinner hozzáadása layouthoz

        layout.setPadding(0, 30, 0, 30);
        alert.setView(layout);

        spMobileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Válassz gyártmányt!")) {
                    selectedMobileType = 0;
                } else {
                    selectedMobileType = adapterView.getSelectedItemPosition();
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
                String etImeiNumber = mobileImeiNumber.getText().toString();

                if (etImeiNumber.equals("") || selectedMobileType == 0) {
                    Toast.makeText(MobileHandle.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean mobileCheck = db.mobileCheck(etImeiNumber);
                    if (!mobileCheck) {
                        boolean createMobile = db.insertMobile(etImeiNumber,selectedMobileType);
                        if (createMobile) {
                            ls.progressDialog(MobileHandle.this, "Mobil felvétele folyamatban...", "Létrehozás");
                        } else
                            Toast.makeText(MobileHandle.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        alert.show();
    }

    private void init() {
        btMobileCreate = findViewById(R.id.btMobileCreate);
        btMobileEdit = findViewById(R.id.btMobileEdit);
        btMobileList = findViewById(R.id.btMobileList);
        btMobileHandleBack = findViewById(R.id.btMobileHandleBack);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileHandle.this);

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
                startActivity(new Intent(MobileHandle.this, Login.class));
                Animatoo.animateFade(MobileHandle.this);
                finish();
            }
        });
        builder.show();
    }
}
