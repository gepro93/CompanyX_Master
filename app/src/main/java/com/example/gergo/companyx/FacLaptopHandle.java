package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class FacLaptopHandle extends AppCompatActivity {

    private Button btFacLaptopEdit, btLaptopMenu, btFacLaptopBack, btFacLaptopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_laptop_handle);
        init();

        btFacLaptopEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, FacLaptopEdit.class));
                Animatoo.animateSlideLeft(FacLaptopHandle.this);
                finish();
            }
        });

        btLaptopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, LaptopMenu.class));
                Animatoo.animateSlideLeft(FacLaptopHandle.this);
                finish();
            }
        });

        btFacLaptopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, FacLaptopList.class));
                Animatoo.animateSlideLeft(FacLaptopHandle.this);
                finish();
            }
        });

        btFacLaptopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, FacilitiesMenu.class));
                Animatoo.animateSlideRight(FacLaptopHandle.this);
                finish();
            }
        });
    }

    private void init() {
        btFacLaptopEdit = findViewById(R.id.btFacLaptopEdit);
        btLaptopMenu = findViewById(R.id.btLaptopMenu);
        btFacLaptopBack = findViewById(R.id.btFacLaptopBack);
        btFacLaptopList = findViewById(R.id.btFacLaptopList);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacLaptopHandle.this);

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
                startActivity(new Intent(FacLaptopHandle.this, Login.class));
                Animatoo.animateFade(FacLaptopHandle.this);
                finish();
            }
        });
        builder.show();
    }
}
