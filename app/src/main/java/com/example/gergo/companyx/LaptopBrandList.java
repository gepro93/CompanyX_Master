package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
                Animatoo.animateSlideRight(LaptopBrandList.this);
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
        adapter = new SimpleAdapter(LaptopBrandList.this, laptopBrandList, R.layout.laptop_brand_edit_row,
                new String[]{"MODELOFLAPTOP_BRAND","MODELOFLAPTOP_TYPE","GRADE_NAME"},
                new int[]{R.id.twLaptopBrand,R.id.twLaptopType,R.id.twGradeName});

        lwLaptopBrandList.setAdapter(adapter);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LaptopBrandList.this);

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
                startActivity(new Intent(LaptopBrandList.this, Login.class));
                Animatoo.animateFade(LaptopBrandList.this);
                finish();
            }
        });
        builder.show();
    }
}
