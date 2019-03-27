package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class CarMenu extends AppCompatActivity {

    private Button btCarHandle, btCarBrandHandle, btCarMenuBack;
    private String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        permission = sp.getString("Permission","Nincs adat");

        btCarMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Facilities")){
                    startActivity(new Intent(CarMenu.this, FacCarHandle.class));
                    Animatoo.animateSlideRight(CarMenu.this);
                    finish();
                }else
                startActivity(new Intent(CarMenu.this, AdminMenu.class));
                Animatoo.animateSlideRight(CarMenu.this);
                finish();
            }
        });

        btCarBrandHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarMenu.this, CarBrandHandle.class));
                Animatoo.animateSlideLeft(CarMenu.this);
                finish();
            }
        });

        btCarHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarMenu.this, CarHandle.class));
                Animatoo.animateSlideLeft(CarMenu.this);
                finish();
            }
        });

    }

    private void init(){
        btCarHandle = findViewById(R.id.btCarHandle);
        btCarBrandHandle = findViewById(R.id.btCarBrandHandle);
        btCarMenuBack = findViewById(R.id.btCarMenuBack);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CarMenu.this);

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
                startActivity(new Intent(CarMenu.this, Login.class));
                Animatoo.animateFade(CarMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
