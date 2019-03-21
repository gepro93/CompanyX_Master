package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                finish();
            }
        });

        btMobileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, MobileMenu.class));
                finish();
            }
        });

        btFacMobileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, FacLaptopList.class));
                finish();
            }
        });

        btFacMobileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacMobileHandle.this, FacilitiesMenu.class));
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
}
