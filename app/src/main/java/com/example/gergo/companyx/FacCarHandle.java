package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class FacCarHandle extends AppCompatActivity {

    private Button btFacCarEdit, btCarMenu, btFacCarBack, btFacCarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_car_handle);
        init();

        btCarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,CarMenu.class));
                Animatoo.animateSlideLeft(FacCarHandle.this);
                finish();
            }
        });

        btFacCarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,FacCarEdit.class));
                Animatoo.animateSlideLeft(FacCarHandle.this);
                finish();
            }
        });

        btFacCarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,FacCarList.class));
                Animatoo.animateSlideLeft(FacCarHandle.this);
                finish();
            }
        });

        btFacCarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,FacilitiesMenu.class));
                Animatoo.animateSlideRight(FacCarHandle.this);
                finish();
            }
        });

    }

    private void init() {
        btFacCarEdit = findViewById(R.id.btFacCarEdit);
        btCarMenu = findViewById(R.id.btCarMenu);
        btFacCarBack = findViewById(R.id.btFacCarBack);
        btFacCarList = findViewById(R.id.btFacCarList);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacCarHandle.this);

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
                startActivity(new Intent(FacCarHandle.this, Login.class));
                Animatoo.animateFade(FacCarHandle.this);
                finish();
            }
        });
        builder.show();
    }
}
