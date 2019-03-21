package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FacLaptopHandle extends AppCompatActivity {

    private Button btFacLaptopEdit, btLaptopMenu, btFacLaptopBack, btFacLaptopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_laptop_handle);
        init();

        btFacLaptopEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, FacLaptopEdit.class));
                finish();
            }
        });

        btLaptopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, LaptopMenu.class));
                finish();
            }
        });

        btFacLaptopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, FacLaptopList.class));
                finish();
            }
        });

        btFacLaptopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacLaptopHandle.this, FacilitiesMenu.class));
                finish();
            }
        });
    }

    private void init() {
        btFacLaptopEdit = findViewById(R.id.btFacLaptopEdit);
        btLaptopMenu = findViewById(R.id.btLaptopMenu);
        btFacLaptopBack = findViewById(R.id.btFacLaptopBack);
        btFacLaptopList = findViewById(R.id.btFacLaptopList);
    }
}
