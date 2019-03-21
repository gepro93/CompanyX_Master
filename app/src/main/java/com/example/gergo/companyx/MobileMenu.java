package com.example.gergo.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                finish();
            }
        });

        btMobileBrandHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileMenu.this, MobileBrandHandle.class));
                finish();
            }
        });

        btMobileMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Facilities")){
                    startActivity(new Intent(MobileMenu.this, FacMobileHandle.class));
                    finish();
                }else
                    startActivity(new Intent(MobileMenu.this, AdminMenu.class));
                finish();
            }
        });

    }

    private void init() {
        btMobileHandle = findViewById(R.id.btMobileHandle);
        btMobileBrandHandle = findViewById(R.id.btMobileBrandHandle);
        btMobileMenuBack = findViewById(R.id.btMobileMenuBack);
    }
}
