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

public class LaptopBrandList extends AppCompatActivity {

    private Button btLaptopBrandListBack;
    private ListView lwLaptopBrandList;
    private Database db;
    private ArrayList<HashMap<String, String>> laptopBrandList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_brand_list);
        init();
        createList();

        btLaptopBrandListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopBrandList.this,LaptopBrandHandle.class));
                finish();
            }
        });
    }

    private void init() {
        btLaptopBrandListBack = findViewById(R.id.btLaptopBrandListBack);
        lwLaptopBrandList = findViewById(R.id.lwLaptopBrandList);
        db = new Database(this);
    }

    private void createList() {
        laptopBrandList = db.viewLaptopBrands();
        adapter = new SimpleAdapter(LaptopBrandList.this, laptopBrandList, R.layout.laptop_brand_list_row,
                new String[]{"MODELOFLAPTOP_BRAND","MODELOFLAPTOP_TYPE","GRADE_NAME"},
                new int[]{R.id.twLaptopBrand,R.id.twLaptopType,R.id.twGradeName});

        lwLaptopBrandList.setAdapter(adapter);
    }
}
