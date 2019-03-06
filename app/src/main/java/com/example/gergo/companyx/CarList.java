package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CarList extends AppCompatActivity {

    private Button btCarListBack;
    private ListView lwCarList;
    private Database db;
    private ArrayList<HashMap<String, String>> carList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        init();
        createList();

        btCarListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarList.this,CarHandle.class));
            }
        });

    }

    private void createList() {
        carList = db.viewCars();
        adapter = new SimpleAdapter(CarList.this, carList, R.layout.car_list_row,
                new String[]{"CAR_LICENSENUMBER","CAR_VINNUMBER","MOTDATE","CARTYPE"},
                new int[]{R.id.twLicenseeNumber,R.id.twVinNumber,R.id.twMotDate,R.id.twCarType});

        lwCarList.setAdapter(adapter);
    }

    private void init(){
        btCarListBack = findViewById(R.id.btCarListBack);
        lwCarList = findViewById(R.id.lwCarList);
        db = new Database(this);
    }
}
