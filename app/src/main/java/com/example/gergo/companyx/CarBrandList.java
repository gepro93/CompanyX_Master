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

public class CarBrandList extends AppCompatActivity {

    private Button btCarBrandListBack;
    private ListView lwCarBrandList;
    private Database db;
    private ArrayList<HashMap<String, String>> carBrandList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_brand_list);

        init();
        createList();

        btCarBrandListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarBrandList.this,CarHandle.class));
            }
        });

    }

    private void createList() {
        carBrandList = db.viewCarBrands();
        adapter = new SimpleAdapter(CarBrandList.this, carBrandList, R.layout.car_brand_list_row,
                new String[]{"MODELOFCAR_BRAND","MODELOFCAR_TYPE","GRADE_NAME"},
                new int[]{R.id.twCarBrand,R.id.twCarType,R.id.twGradeName});

        lwCarBrandList.setAdapter(adapter);
    }

    private void init(){
        btCarBrandListBack = findViewById(R.id.btCarBrandListBack);
        lwCarBrandList = findViewById(R.id.lwCarBrandList);
        db = new Database(this);
    }
}
