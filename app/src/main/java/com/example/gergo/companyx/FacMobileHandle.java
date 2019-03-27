package com.example.gergo.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class FacMobileHandle extends AppCompatActivity {

    private Button btFacMobileEdit, btMobileMenu, btFacMobileBack, btFacMobileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_mobile_handle);
        init();

        btFacMobileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, FacMobileEdit.class));
                Animatoo.animateSlideLeft(FacMobileHandle.this);
                finish();
            }
        });

        btMobileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, MobileMenu.class));
                Animatoo.animateSlideLeft(FacMobileHandle.this);
                finish();
            }
        });

        btFacMobileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, FacLaptopList.class));
                Animatoo.animateSlideLeft(FacMobileHandle.this);
                finish();
            }
        });

        btFacMobileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, FacilitiesMenu.class));
                Animatoo.animateSlideRight(FacMobileHandle.this);
                finish();
            }
        });
    }

    private void init() {
        btFacMobileEdit = findViewById(R.id.btFacMobileEdit);
        btMobileMenu = findViewById(R.id.btMobileMenu);
        btFacMobileBack = findViewById(R.id.btFacMobileBack);
        btFacMobileList = findViewById(R.id.btFacMobileList);
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FacMobileHandle.this);

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
                startActivity(new Intent(FacMobileHandle.this, Login.class));
                Animatoo.animateFade(FacMobileHandle.this);
                finish();
            }
        });
        builder.show();
    }
}
