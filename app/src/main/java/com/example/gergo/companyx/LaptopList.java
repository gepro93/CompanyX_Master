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

public class LaptopList extends AppCompatActivity {

    private Button btLaptopListBack;
    private ListView lwLaptopList;
    private Database db;
    private ArrayList<HashMap<String, String>> laptopList;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_list);
        init();
        createList();

        btLaptopListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopList.this, LaptopHandle.class));
                Animatoo.animateSlideRight(LaptopList.this);
                finish();
            }
        });

    }

    private void init() {
        btLaptopListBack = findViewById(R.id.btLaptopListBack);
        lwLaptopList = findViewById(R.id.lwLaptopList);
        db = new Database(this);
    }

    private void createList() {
        laptopList = db.viewLaptops();
        adapter = new SimpleAdapter(LaptopList.this, laptopList, R.layout.laptop_list_row,
                new String[]{"LAPTOPTYPE","LAPTOP_IMEINUMBER"},
                new int[]{R.id.twModelType,R.id.twImeiNumber});

        lwLaptopList.setAdapter(adapter);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LaptopList.this);

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
                startActivity(new Intent(LaptopList.this, Login.class));
                Animatoo.animateFade(LaptopList.this);
                finish();
            }
        });
        builder.show();
    }
}
