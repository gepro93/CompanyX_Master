package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CarMenu extends AppCompatActivity {

    private Button btCarHandle, btCarBrandHandle, btCarMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu);
        init();

        btCarMenuBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
