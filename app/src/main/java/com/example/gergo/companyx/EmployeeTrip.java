package com.example.gergo.companyx;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class EmployeeTrip extends AppCompatActivity {

    private EditText etKmData;
    private Button btStartTrip, btFinishTrip, btEmployeeCarMenuBack, btEmployeeCarMenuCancel, btSaveTrip;
    private LottieAnimationView ltCar;
    private int kmStart, kmEnd;
    private float latitude, longitude;
    private String gpsStart, gpsEnd, userName;
    private Database db;
    private int empId;
    private LoadScreen ls;
    private TextView indulaskor, kmstart, twKmStart, gpsstart, twGpsStart, erkezeskor, kmend, twKmEnd, gpsend, twGpsEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_trip);
        init();
        onCreateClickable();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        userName = sp.getString("LoginUserName","Nincs adat");

        empId = db.empIdByUserName(userName);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = (float) location.getLatitude();
                longitude = (float) location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        btStartTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etKmData.equals("")){
                gpsStart = longitude+","+latitude;
                kmStart = Integer.parseInt(etKmData.getText().toString());
                hideStartScreen();
                showFinishScreen();
                etKmData.setText("");
                etKmData.clearFocus();
                }else etKmData.setError("Km óra állás megadása kötelező!");
                clearFocus(view);
            }
        });

        btFinishTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etKmData.equals("")){
                    etKmData.setError("Km óra állás megadása kötelező!");
                }else{
                    kmEnd = Integer.parseInt(etKmData.getText().toString());
                    if(kmStart >= kmEnd){
                        etKmData.setError("Km óra állás kevesebb mint induláskor!");
                    }else {
                        hideFinishScreen();
                        showResult();
                        gpsEnd = longitude + "," + latitude;
                        twKmStart.setText(Integer.toString(kmStart));
                        twGpsStart.setText(gpsStart);
                        twKmEnd.setText(Integer.toString(kmEnd));
                        twGpsEnd.setText(gpsEnd);
                        etKmData.setText("");
                    }
                }
                clearFocus(view);
            }
        });

        btSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(empId != 0 && !gpsStart.equals("") && !gpsEnd.equals("") && kmStart != 0 && kmEnd != 0) {
                    boolean saveTrip = db.insertTrip(empId, gpsStart, gpsEnd, kmStart, kmEnd);
                    if (saveTrip) {
                        ls.progressDialog(EmployeeTrip.this, "Rögzítés folyamatban...", "Út mentése");
                        hideResult();
                        showStartScreen();
                    } else Toast.makeText(EmployeeTrip.this, "Nem sikerült rögzíteni az utat!", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(EmployeeTrip.this, "Hiba a program futása közben!", Toast.LENGTH_SHORT).show();

            }
        });

        btEmployeeCarMenuCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kmStart = 0;
                kmEnd = 0;
                gpsStart = "";
                gpsEnd = "";
                hideFinishScreen();
                hideResult();
                showStartScreen();
            }
        });

        btEmployeeCarMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeTrip.this, EmployeeCarMenu.class));
                Animatoo.animateSlideRight(EmployeeTrip.this);
                finish();
            }
        });
    }
    private void clearFocus(View view){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void onCreateClickable(){
        btStartTrip.setEnabled(true);
        btEmployeeCarMenuBack.setEnabled(true);
        btFinishTrip.setEnabled(false);
        btEmployeeCarMenuCancel.setEnabled(false);
        btSaveTrip.setEnabled(false);
        ltCar.setVisibility(View.INVISIBLE);
    }

    private void showStartScreen(){
        etKmData.setAlpha(1);
        btStartTrip.setAlpha(1);
        btEmployeeCarMenuBack.setAlpha(1);
        btStartTrip.setEnabled(true);
        btEmployeeCarMenuBack.setEnabled(true);
    }

    private void hideStartScreen(){
        btStartTrip.setAlpha(0);
        btEmployeeCarMenuBack.setAlpha(0);
        btStartTrip.setEnabled(false);
        btEmployeeCarMenuBack.setEnabled(false);
    }

    private void showFinishScreen(){
        btFinishTrip.setAlpha(1);
        btEmployeeCarMenuCancel.setAlpha(1);
        btFinishTrip.setEnabled(true);
        btEmployeeCarMenuCancel.setEnabled(true);
        ltCar.setVisibility(View.VISIBLE);
    }

    private void hideFinishScreen(){
        etKmData.setAlpha(0);
        btFinishTrip.setAlpha(0);
        btEmployeeCarMenuCancel.setAlpha(0);
        btFinishTrip.setEnabled(false);
        btEmployeeCarMenuCancel.setEnabled(false);
        ltCar.setVisibility(View.INVISIBLE);
    }

    private void showResult(){
        indulaskor.setAlpha(1);
        kmstart.setAlpha(1);
        twKmStart.setAlpha(1);
        gpsstart.setAlpha(1);
        twGpsStart.setAlpha(1);
        erkezeskor.setAlpha(1);
        kmend.setAlpha(1);
        twKmEnd.setAlpha(1);
        gpsend.setAlpha(1);
        twGpsEnd.setAlpha(1);
        btSaveTrip.setAlpha(1);
        btEmployeeCarMenuBack.setAlpha(1);
        btSaveTrip.setEnabled(true);
        btEmployeeCarMenuBack.setEnabled(true);

    }

    private void hideResult(){
        indulaskor.setAlpha(0);
        kmstart.setAlpha(0);
        twKmStart.setAlpha(0);
        gpsstart.setAlpha(0);
        twGpsStart.setAlpha(0);
        erkezeskor.setAlpha(0);
        kmend.setAlpha(0);
        twKmEnd.setAlpha(0);
        gpsend.setAlpha(0);
        twGpsEnd.setAlpha(0);
        btSaveTrip.setAlpha(0);
        btSaveTrip.setClickable(false);
    }

    private void init() {
        etKmData =  findViewById(R.id.etKmData);
        btStartTrip =  findViewById(R.id.btStartTrip);
        btFinishTrip =  findViewById(R.id.btFinishTrip);
        btSaveTrip =  findViewById(R.id.btSaveTrip);
        btEmployeeCarMenuBack =  findViewById(R.id.btEmployeeCarMenuBack);
        btEmployeeCarMenuCancel =  findViewById(R.id.btEmployeeCarMenuCancel);
        ltCar =  findViewById(R.id.ltCar);
        db = new Database(this);
        ls = new LoadScreen();

        indulaskor = findViewById(R.id.indulaskor);
        kmstart = findViewById(R.id.kmstart);
        twKmStart = findViewById(R.id.twKmStart);
        gpsstart = findViewById(R.id.gpsstart);
        twGpsStart = findViewById(R.id.twGpsStart);
        erkezeskor = findViewById(R.id.erkezeskor);
        kmend = findViewById(R.id.kmend);
        twKmEnd = findViewById(R.id.twKmEnd);
        gpsend = findViewById(R.id.gpsend);
        twGpsEnd = findViewById(R.id.twGpsEnd);
    }
}
