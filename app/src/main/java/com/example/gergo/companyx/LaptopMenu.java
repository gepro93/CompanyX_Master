package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaptopMenu extends AppCompatActivity {

    private Button btLaptopHandle, btLaptopBrandHandle, btLaptopMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_menu);
        init();

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
