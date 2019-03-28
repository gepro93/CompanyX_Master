package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;

public class EmployeeTripList extends AppCompatActivity {

    private Spinner spTripDate;
    private Button btLoadTrip, btEmployeeTripListBack;
    private ArrayList<String> tripList, tripDetails;
    private Database db;
    private String userName, selectedTripDate, kmStart, kmEnd, gpsStart, gpsEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_trip_list);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        fillSpinner();

        spTripDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTripDate = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btLoadTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTrip();
            }
        });

        btEmployeeTripListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeTripList.this, EmployeeCarMenu.class));
                Animatoo.animateSlideRight(EmployeeTripList.this);
                finish();
            }
        });
    }

    private void fillSpinner(){
        String query = "SELECT rogzitesIdo AS rogzitesIdo" +
                " FROM utak AS u" +
                " LEFT JOIN dolgozok AS d ON d.dolg_id = u.dolgozo_id" +
                " LEFT JOIN felhasznalok AS f ON f.felh_id = d.felh_id" +
                " WHERE felhNeve = "+"'"+userName+"'";
        tripList = db.queryFillList(query, "rogzitesIdo");
        tripList.add(0,"Válassz rögzítési időpontot!");
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tripList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTripDate.setAdapter(arrayAdapter);
    }

    private void viewTrip() {
        String sqlQuery = "SELECT u.kmIndulas AS kmIndulas, u.gpsIndulas AS gpsIndulas, u.kmErkezes AS kmErkezes, u.gpsErkezes AS gpsErkezes" +
                " FROM utak AS u" +
                " LEFT JOIN dolgozok AS d ON d.dolg_id = u.dolgozo_id" +
                " LEFT JOIN felhasznalok AS f ON f.felh_id = d.felh_id" +
                " WHERE f.felhNeve="+"'"+userName+"' AND u.rogzitesIdo="+"'"+selectedTripDate+"'";
        tripDetails = db.tripDetails(sqlQuery,"kmIndulas", "gpsIndulas", "kmErkezes", "gpsErkezes");

        if (tripDetails.size()>0){
            kmStart = tripDetails.get(0);
            gpsStart = tripDetails.get(1);
            kmEnd = tripDetails.get(2);
            gpsEnd = tripDetails.get(3);

            AlertDialog.Builder alert = new AlertDialog.Builder(EmployeeTripList.this);

            alert.setTitle("Adatbázis találat!");

            //Linear Layout felépítése
            LinearLayout layout = new LinearLayout(EmployeeTripList.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            //TextView létrehozása
            final TextView indulas = new TextView(EmployeeTripList.this);

            indulas.setPadding(80, 60, 0, 30);
            indulas.setText("Induláskor");
            indulas.setTypeface(Typeface.DEFAULT_BOLD);
            layout.addView(indulas); //TextView hozzáadása layouthoz

            LinearLayout llkmstart = new LinearLayout(EmployeeTripList.this);
            llkmstart.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(llkmstart);

            //TextView létrehozása
            final TextView twKmStart = new TextView(EmployeeTripList.this);

            twKmStart.setPadding(40, 30, 0, 30);
            twKmStart.setText("Km óra állása: ");
            llkmstart.addView(twKmStart); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView twKmStartData = new TextView(EmployeeTripList.this);

            twKmStartData.setPadding(40, 30, 0, 30);
            twKmStartData.setText(kmStart);
            llkmstart.addView(twKmStartData); //TextView hozzáadása layouthoz

            LinearLayout llgpsstart = new LinearLayout(EmployeeTripList.this);
            llgpsstart.setOrientation(LinearLayout.HORIZONTAL);

            layout.addView(llgpsstart);

            //TextView létrehozása
            final TextView twGpsStart = new TextView(EmployeeTripList.this);
            twGpsStart.setPadding(40, 30, 0, 30);
            twGpsStart.setText("GPS koord.:: ");
            llgpsstart.addView(twGpsStart); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView twGpsStartData = new TextView(EmployeeTripList.this);
            twGpsStartData.setPadding(40, 30, 0, 30);
            twGpsStartData.setText(gpsStart);
            llgpsstart.addView(twGpsStartData); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView erkezes = new TextView(EmployeeTripList.this);

            erkezes.setPadding(80, 60, 0, 30);
            erkezes.setText("Érkezéskor");
            erkezes.setTypeface(Typeface.DEFAULT_BOLD);
            layout.addView(erkezes); //TextView hozzáadása layouthoz

            LinearLayout llkmend = new LinearLayout(EmployeeTripList.this);
            llkmend.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(llkmend);

            //TextView létrehozása
            final TextView twKmEnd = new TextView(EmployeeTripList.this);
            twKmEnd.setGravity(Gravity.CENTER);
            twKmEnd.setPadding(40, 30, 0, 30);
            twKmEnd.setText("Km óra állása: ");
            llkmend.addView(twKmEnd); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView twKmEndData = new TextView(EmployeeTripList.this);
            twKmEndData.setGravity(Gravity.CENTER);
            twKmEndData.setPadding(40, 30, 0, 30);
            twKmEndData.setText(kmEnd);
            llkmend.addView(twKmEndData); //TextView hozzáadása layouthoz

            LinearLayout llgpsend = new LinearLayout(EmployeeTripList.this);
            llgpsend.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(llgpsend);

            //TextView létrehozása
            final TextView twGpsEnd = new TextView(EmployeeTripList.this);
            twGpsEnd.setGravity(Gravity.CENTER);
            twGpsEnd.setPadding(40, 30, 0, 30);
            twGpsEnd.setText("GPS koord.:: ");
            llgpsend.addView(twGpsEnd); //TextView hozzáadása layouthoz

            //TextView létrehozása
            final TextView twGpsEndData = new TextView(EmployeeTripList.this);
            twGpsEndData.setPadding(40, 30, 0, 30);
            twGpsEndData.setText(gpsEnd);
            llgpsend.addView(twGpsEndData); //TextView hozzáadása layouthoz

            alert.setView(layout);

            alert.setPositiveButton("Oké", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }else Toast.makeText(this, "Adatbázis hiba!", Toast.LENGTH_SHORT).show();


    }

    private void init() {
        spTripDate = findViewById(R.id.spTripDate);
        btLoadTrip = findViewById(R.id.btLoadTrip);
        btEmployeeTripListBack = findViewById(R.id.btEmployeeTripListBack);
        db = new Database(this);
    }
}
