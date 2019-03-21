package com.example.gergo.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                finish();
            }
        });

        btLaptopBrandHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaptopMenu.this, LaptopBrandHandle.class));
                finish();
            }
        });

        btLaptopMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permission.equals("Facilities")){
                    startActivity(new Intent(LaptopMenu.this, FacLaptopHandle.class));
                    finish();
                }else
                    startActivity(new Intent(LaptopMenu.this, AdminMenu.class));
                finish();
            }
        });
    }

    private void init() {
        btLaptopHandle = findViewById(R.id.btLaptopHandle);
        btLaptopBrandHandle = findViewById(R.id.btLaptopBrandHandle);
        btLaptopMenuBack = findViewById(R.id.btLaptopMenuBack);
    }
}
