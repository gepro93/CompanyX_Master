package com.example.gergo.companyx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FacCarHandle extends AppCompatActivity {

    private Button btFacCarEdit, btCarMenu, btFacCarBack, btFacCarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_car_handle);
        init();

        btCarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,CarMenu.class));
                finish();
            }
        });

        btFacCarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,FacCarEdit.class));
                finish();
            }
        });

        btFacCarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,FacCarList.class));
                finish();
            }
        });

        btFacCarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacCarHandle.this,FacilitiesMenu.class));
                finish();
            }
        });

    }

    private void init() {
        btFacCarEdit = findViewById(R.id.btFacCarEdit);
        btCarMenu = findViewById(R.id.btCarMenu);
        btFacCarBack = findViewById(R.id.btFacCarBack);
        btFacCarList = findViewById(R.id.btFacCarList);
    }
}
