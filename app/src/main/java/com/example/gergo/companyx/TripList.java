package com.example.gergo.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;

public class TripList extends AppCompatActivity {

    private Button btTripListBack;
    private ListView lwTripList;
    private Database db;
    private ArrayList<HashMap<String, String>> tripList;
    private ListAdapter adapter;
    private String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        init();
        createList();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        permission = sp.getString("Permission","Nincs adat");

        btTripListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Facilities")){
                    startActivity(new Intent(TripList.this, FacilitiesMenu.class));
                    Animatoo.animateSlideRight(TripList.this);
                    finish();
                }else
                    startActivity(new Intent(TripList.this, AdminMenu.class));
                Animatoo.animateSlideRight(TripList.this);
                finish();

            }
        });
    }

    private void init() {
        btTripListBack = findViewById(R.id.btTripListBack);
        lwTripList = findViewById(R.id.lwTripList);
        db = new Database(this);
    }

    private void createList() {
        tripList = db.viewTrips();
        adapter = new SimpleAdapter(TripList.this, tripList, R.layout.trip_list_row,
                new String[]{"dolgNev","kmIndulas","gpsIndulas","kmErkezes","gpsErkezes","rogzitesIdo"},
                new int[]{R.id.twEmployeeName,R.id.twKmStart,R.id.twGpsStart,R.id.twKmEnd,R.id.twGpsEnd,R.id.twDate});

        lwTripList.setAdapter(adapter);
    }
}
