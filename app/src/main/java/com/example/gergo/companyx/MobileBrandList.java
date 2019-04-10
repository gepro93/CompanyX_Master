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

public class MobileBrandList extends AppCompatActivity {

    private Button btMobileBrandListBack;
    private ListView lwMobileBrandList;
    private Database db;
    private ArrayList<HashMap<String, String>> mobileBrandList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_brand_list);
        init();
        createList();

        btMobileBrandListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileBrandList.this,MobileBrandHandle.class));
                Animatoo.animateSlideRight(MobileBrandList.this);
                finish();
            }
        });
    }

    private void init() {
        btMobileBrandListBack = findViewById(R.id.btMobileBrandListBack);
        lwMobileBrandList = findViewById(R.id.lwMobileBrandList);
        db = new Database(this);
    }

    private void createList() {
        mobileBrandList = db.viewMobileBrands();
        adapter = new SimpleAdapter(MobileBrandList.this, mobileBrandList, R.layout.mobile_brand_edit_row,
                new String[]{"MODELOFMOBIL_BRAND","MODELOFMOBIL_TYPE","GRADE_NAME"},
                new int[]{R.id.twMobileBrand,R.id.twMobileType,R.id.twGradeName});

        lwMobileBrandList.setAdapter(adapter);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileBrandList.this);

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
                startActivity(new Intent(MobileBrandList.this, Login.class));
                Animatoo.animateFade(MobileBrandList.this);
                finish();
            }
        });
        builder.show();
    }
}
