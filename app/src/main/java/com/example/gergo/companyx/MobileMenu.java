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

public class MobileMenu extends AppCompatActivity {

    private Button btMobileHandle, btMobileBrandHandle, btMobileMenuBack;
    private String permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_menu);
        init();

        SharedPreferences sp = getSharedPreferences("LoginDetails",MODE_PRIVATE);
        permission = sp.getString("Permission","Nincs adat");

        btMobileHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileMenu.this, MobileHandle.class));
                Animatoo.animateSlideLeft(MobileMenu.this);
                finish();
            }
        });

        btMobileBrandHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileMenu.this, MobileBrandHandle.class));
                Animatoo.animateSlideLeft(MobileMenu.this);
                finish();
            }
        });

        btMobileMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Facilities")){
                    startActivity(new Intent(MobileMenu.this, FacMobileHandle.class));
                    Animatoo.animateSlideRight(MobileMenu.this);
                    finish();
                }else
                    startActivity(new Intent(MobileMenu.this, AdminMenu.class));
                    Animatoo.animateSlideRight(MobileMenu.this);
                    finish();
            }
        });

    }

    private void init() {
        btMobileHandle = findViewById(R.id.btMobileHandle);
        btMobileBrandHandle = findViewById(R.id.btMobileBrandHandle);
        btMobileMenuBack = findViewById(R.id.btMobileMenuBack);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileMenu.this);

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
                startActivity(new Intent(MobileMenu.this, Login.class));
                Animatoo.animateFade(MobileMenu.this);
                finish();
            }
        });
        builder.show();
    }
}
