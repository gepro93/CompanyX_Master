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
import java.util.List;

public class PositionMenu extends AppCompatActivity {

    private Button btPositionCreate, btPositionModify, btPositionDelete, btPositionList, btPositionMenuBack;
    private Database db;
    private int gradeId;
    private LoadScreen ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_menu);
        init();

        btPositionCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPermission();
            }
        });

        btPositionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionMenu.this,PositionList.class));
                Animatoo.animateSlideLeft(PositionMenu.this);
                finish();
            }
        });

        btPositionModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionMenu.this,PositionModify.class));
                Animatoo.animateSlideLeft(PositionMenu.this);
                finish();
            }
        });

        btPositionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionMenu.this,PositionDelete.class));
                Animatoo.animateSlideLeft(PositionMenu.this);
                finish();
            }
        });

        btPositionMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositionMenu.this,AdminMenu.class));
                Animatoo.animateSlideRight(PositionMenu.this);
                finish();
            }
        });
    }

    public void init(){
        btPositionCreate = (Button) findViewById(R.id.btPositionCreate);
        btPositionModify = (Button) findViewById(R.id.btPositionModify);
        btPositionDelete = (Button) findViewById(R.id.btPositionDelete);
        btPositionList = (Button) findViewById(R.id.btPositionList);
        btPositionMenuBack = (Button) findViewById(R.id.btPositionMenuBack);
        db = new Database(this);
        ls = new LoadScreen();
    }

    public void createPermission(){
        AlertDialog.Builder alert = new AlertDialog.Builder(PositionMenu.this);

        alert.setMessage("Beosztás megnevezése:");
        alert.setTitle("Létrehozás");

        //Linear Layout felépítése
        LinearLayout layout = new LinearLayout(PositionMenu.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Edit Text létrehozása
        final EditText position = new EditText(PositionMenu.this);
        position.setHint("Beosztás");
        position.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        position.setGravity(Gravity.CENTER);
        position.setPadding(0,0,0,60);
        layout.addView(position); //Edit Text hozzáadása layouthoz

        //Spinner létrehozása
        final Spinner grade = new Spinner(PositionMenu.this,Spinner.MODE_DROPDOWN);

        //Spinner tartalmának elkészítése listában
        final List<String> grades = new ArrayList<>();
        grades.add(0, "Fizetés besorolás");
        grades.add("Grade1");
        grades.add("Grade2");
        grades.add("Grade3");
        grades.add("Grade4");

        //Grade lista adapter létrehozása
        ArrayAdapter<String> spinnerDataAdapter;
        spinnerDataAdapter = new ArrayAdapter(PositionMenu.this, android.R.layout.simple_spinner_item, grades);
        spinnerDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grade.setAdapter(spinnerDataAdapter);
        grade.setBackgroundResource(R.color.colorWhite);
        layout.addView(grade); //Spinner hozzáadása layouthoz

        layout.setPadding(0,30,0,30);
        alert.setView(layout);

        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).equals("Fizetés besorolás")) {
                    gradeId = 0;
                } else {
                    gradeId = adapterView.getSelectedItemPosition();
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
                String etPosition = position.getText().toString();

                if (etPosition.equals("") || gradeId == 0){
                    Toast.makeText(PositionMenu.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean positionCheck = db.positionCheck(etPosition);
                    if (!positionCheck){
                        boolean createPosition = db.insertPosition(etPosition,gradeId);
                        if (createPosition){
                            ls.progressDialog(PositionMenu.this,"Pozíció létrehozása folyamatban...", "Létrehozás");
                        }else Toast.makeText(PositionMenu.this, "Adatbázis hiba létrehozáskor!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        alert.show();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PositionMenu.this);

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
                startActivity(new Intent(PositionMenu.this, Login.class));
                Animatoo.animateFade(PositionMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
