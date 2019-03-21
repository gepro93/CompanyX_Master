package com.example.gergo.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                    finish();
                }else
                startActivity(new Intent(CarMenu.this, AdminMenu.class));
                finish();
            }
        });

        btCarBrandHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarMenu.this, CarBrandHandle.class));
                finish();
            }
        });

        btCarHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarMenu.this, CarHandle.class));
                finish();
            }
        });

    }

    private void init(){
        btCarHandle = findViewById(R.id.btCarHandle);
        btCarBrandHandle = findViewById(R.id.btCarBrandHandle);
        btCarMenuBack = findViewById(R.id.btCarMenuBack);
    }
}
