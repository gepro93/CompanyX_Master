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

public class LaptopMenu extends AppCompatActivity {

    private Button btLaptopHandle, btLaptopBrandHandle, btLaptopMenuBack;
    private String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        permission = sp.getString("Permission","Nincs adat");

        btLaptopHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopMenu.this, LaptopHandle.class));
                Animatoo.animateSlideLeft(LaptopMenu.this);
                finish();
            }
        });

        btLaptopBrandHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopMenu.this, LaptopBrandHandle.class));
                Animatoo.animateSlideLeft(LaptopMenu.this);
                finish();
            }
        });

        btLaptopMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Facilities")){
                    startActivity(new Intent(LaptopMenu.this, FacLaptopHandle.class));
                    Animatoo.animateSlideRight(LaptopMenu.this);
                    finish();
                }else
                    startActivity(new Intent(LaptopMenu.this, AdminMenu.class));
                    Animatoo.animateSlideRight(LaptopMenu.this);
                    finish();
            }
        });
    }

    private void init() {
        btLaptopHandle = findViewById(R.id.btLaptopHandle);
        btLaptopBrandHandle = findViewById(R.id.btLaptopBrandHandle);
        btLaptopMenuBack = findViewById(R.id.btLaptopMenuBack);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LaptopMenu.this);

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
                startActivity(new Intent(LaptopMenu.this, Login.class));
                Animatoo.animateFade(LaptopMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
